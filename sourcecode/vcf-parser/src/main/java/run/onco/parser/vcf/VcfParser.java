package run.onco.parser.vcf;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.io.Closeables;

import run.onco.parser.vcf.model.BaseMetadata;
import run.onco.parser.vcf.model.ContigMetadata;
import run.onco.parser.vcf.model.FormatMetadata;
import run.onco.parser.vcf.model.IdDescriptionMetadata;
import run.onco.parser.vcf.model.IdMetadata;
import run.onco.parser.vcf.model.InfoMetadata;
import run.onco.parser.vcf.model.VcfMetadata;
import run.onco.parser.vcf.model.VcfPosition;
import run.onco.parser.vcf.model.VcfSample;

/**
 * This class parses a VCF file.
 *
 * @author Mark Woon
 */
public class VcfParser implements Closeable {

	private final static Logger logger = Logger.getLogger(IdMetadata.class);

	private static final Pattern sf_tabSplitter = Pattern.compile("\t");
	private static final Pattern sf_commaSplitter = Pattern.compile(",");
	private static final Pattern sf_colonSplitter = Pattern.compile(":");
	private static final Pattern sf_semicolonSplitter = Pattern.compile(";");

	private boolean m_rsidsOnly;
	private BufferedReader m_reader;
	private VcfMetadata m_vcfMetadata;
	private VcfLineParser m_vcfLineParser;

	private int m_lineNumber;
	private boolean m_alreadyFinished;

	private VcfParser(@Nonnull BufferedReader reader, boolean rsidsOnly, @Nonnull VcfLineParser lineParser) {
		m_reader = reader;
		m_rsidsOnly = rsidsOnly;
		m_vcfLineParser = lineParser;
	}

	/**
	 * Parses metadata only. This method should be if only the metadata is needed;
	 * otherwise, {@link #parse()} is preferred.
	 */
	public @Nonnull VcfMetadata parseMetadata() throws IOException {

		if (m_vcfMetadata != null) {
			throw new IllegalStateException("Metadata has already been parsed.");
		}
		VcfMetadata.Builder mdBuilder = new VcfMetadata.Builder();
		String line;
		while ((line = m_reader.readLine()) != null) {
			m_lineNumber++;
			if (line.startsWith("##")) {
				try {
					parseMetadata(mdBuilder, line);
				} catch (RuntimeException e) {
					throw new IllegalArgumentException("Error parsing metadata on line #" + m_lineNumber + ": " + line,
							e);
				}
			} else if (line.startsWith("#")) {
				try {
					parseColumnInfo(mdBuilder, line);
				} catch (RuntimeException e) {
					throw new IllegalArgumentException(
							"Error parsing column (# header) on line #" + m_lineNumber + ": " + line, e);
				}
				break;
			}
		}
		m_vcfMetadata = mdBuilder.build();

		// check sample lists
		if (m_vcfMetadata.getNumSamples() == m_vcfMetadata.getSamples().size()) {
			for (int i = 0; i < m_vcfMetadata.getNumSamples(); i++) {
				String sampleName = m_vcfMetadata.getSampleName(i);
				if (!m_vcfMetadata.getSamples().containsKey(sampleName)) {
					logger.warn(String.format("Sample %s is missing in the metadata", sampleName));
				}
			}
		} else {
			logger.warn(String.format("There are %s samples in the header but %s in the metadata", m_vcfMetadata.getNumSamples(),
					m_vcfMetadata.getSamples().size()));
		}

		return m_vcfMetadata;
	}

	/**
	 * Gets VCF metadata (if it has already been parsed).
	 */
	public @Nullable VcfMetadata getMetadata() {
		return m_vcfMetadata;
	}

	/**
	 * Parses the entire VCF file (including the metadata).
	 *
	 * This is the preferred way to read a VCF file.
	 */
	public void parse() throws IOException {
		boolean hasNext = true;
		while (hasNext) {
			hasNext = parseNextLine();
		}
		Closeables.closeQuietly(m_reader);
	}

	/**
	 * Parses just the next data line available, also reading all the metadata if it
	 * has not been read. This is a specialized method; in general calling
	 * {@link #parse()} to parse the entire stream is preferred.
	 *
	 * @return Whether another line may be available to read; false only if and only
	 *         if this is the last line available
	 * @throws IllegalStateException
	 *             If the stream was already fully parsed
	 */
	public boolean parseNextLine() throws IOException {

		if (m_vcfMetadata == null) {
			parseMetadata();
		}

		String line = m_reader.readLine();
		if (line == null) {
			m_alreadyFinished = true;
			return false;
		}

		if (m_alreadyFinished) {
			// prevents user errors from causing infinite loops
			throw new IllegalStateException("Already finished reading the stream");
		}

		m_lineNumber++;

		try {

			List<String> data = toList(sf_tabSplitter, line);

			// CHROM
			String chromosome = data.get(0);

			// POS
			long position;
			try {
				position = Long.parseLong(data.get(1));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Error parsing VCF data line #" + m_lineNumber + ": POS " + data.get(1) + " is not a number");
			}

			// ID
			List<String> ids = null;
			if (!data.get(2).equals(".")) {
				if (m_rsidsOnly && !VcfUtils.RSID_PATTERN.matcher(data.get(2)).find()) {
					return true;
				}
				ids = toList(sf_semicolonSplitter, data.get(2));
			} else if (m_rsidsOnly) {
				return true;
			}

			// REF
			String ref = data.get(3);

			// ALT
			List<String> alt = null;
			if (!data.get(7).isEmpty() && !data.get(4).equals(".")) {
				alt = toList(sf_commaSplitter, data.get(4));
			}

			// QUAL
			BigDecimal quality = null;
			if (!data.get(5).isEmpty() && !data.get(5).equals(".")) {
				try {
					quality = new BigDecimal(data.get(5));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Error parsing VCF data line #" + m_lineNumber + ": QUAL " + data.get(5) + " is not a number");
				}
			}

			// FILTER
			List<String> filters = null;
			if (!("PASS").equals(data.get(6))) {
				filters = toList(sf_semicolonSplitter, data.get(6));
			}

			// INFO
			ListMultimap<String, String> info = null;
			if (!("").equals(data.get(7)) && !(".").equals(data.get(7))) {
				info = ArrayListMultimap.create();
				List<String> props = toList(sf_semicolonSplitter, data.get(7));
				for (String prop : props) {
					int idx = prop.indexOf('=');
					if (idx == -1) {
						info.put(prop, "");
					} else {
						String key = prop.substring(0, idx);
						String value = prop.substring(idx + 1);
						
						List<String> list = toList(sf_commaSplitter, value);
						info.putAll(key, list);
						
//						for(String x : list) {
//							logger.info(String.format("-----> INFO[%s]: %s", key, x));
//						}
					}
				}
			}

			// FORMAT
			List<String> format = null;
			if (data.size() >= 9 && data.get(8) != null) {
				format = toList(sf_colonSplitter, data.get(8));
			}

			// samples
			VcfPosition pos = new VcfPosition(chromosome, position, ids, ref, alt, quality, filters, info, format);
			List<VcfSample> samples = new ArrayList<VcfSample>();
			for (int x = 9; x < data.size(); x++) {
				samples.add(new VcfSample(format, toList(sf_colonSplitter, (data.get(x)))));
			}

			m_vcfLineParser.parseLine(m_vcfMetadata, pos, samples);

			m_lineNumber++;

		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("Error parsing VCF data line #" + m_lineNumber + ": " + line, e);
		}
		return true;
	}

	@Override
	public void close() {
		Closeables.closeQuietly(m_reader);
	}

	/**
	 * Parses a metadata line (starts with ##).
	 */
	private void parseMetadata(@Nonnull VcfMetadata.Builder mdBuilder, @Nonnull String line) {

		int idx = line.indexOf("=");
		String propName = line.substring(2, idx).trim();
		String propValue = line.substring(idx + 1).trim();

		logger.debug(String.format("%s : %s", propName, propValue));

		switch (propName) {
		case "fileformat":
			mdBuilder.setFileFormat(propValue);
			break;

		case "ALT":
		case "FILTER":
		case "INFO":
		case "FORMAT":
		case "contig":
		case "SAMPLE":
		case "PEDIGREE":
			parseMetadataProperty(mdBuilder, propName, removeAngleBrackets(propValue));
			break;

		case "assembly":
		case "pedigreeDB":
		default:
			mdBuilder.addRawProperty(propName, propValue);
		}
	}

	/**
	 * Removes double quotation marks around a string.
	 * 
	 * @throws IllegalArgumentException
	 *             If angle brackets are not present
	 */
	private static @Nonnull String removeAngleBrackets(@Nonnull String string) throws IllegalArgumentException {
		if (string.startsWith("<") && string.endsWith(">")) {
			return string.substring(1, string.length() - 1);
		}
		throw new IllegalArgumentException("Angle brackets not present for: " + string);
	}

	/**
	 * Converts metadata name-value pair into object.
	 */
	private void parseMetadataProperty(@Nonnull VcfMetadata.Builder mdBuilder, @Nonnull String propName,
			@Nonnull String value) {
		Map<String, String> props = VcfUtils.extractPropertiesFromLine(value);
		switch (propName.toLowerCase()) {
		case "alt":
			mdBuilder.addAlt(new IdDescriptionMetadata(props, true));
			break;
		case "filter":
			mdBuilder.addFilter(new IdDescriptionMetadata(props, true));
			break;
		case "info":
			mdBuilder.addInfo(new InfoMetadata(props));
			break;
		case "format":
			mdBuilder.addFormat(new FormatMetadata(props));
			break;
		case "contig":
			mdBuilder.addContig(new ContigMetadata(props));
			break;
		case "sample":
			mdBuilder.addSample(new IdDescriptionMetadata(props, true));
			break;
		case "pedigree":
			mdBuilder.addPedigree(new BaseMetadata(props));
			break;
		}
	}

	private @Nonnull List<String> toList(@Nonnull Pattern pattern, @Nullable String string) {
		String[] array = pattern.split(string);
		List<String> list = new ArrayList<String>(array.length);
		Collections.addAll(list, array);
		return list;
	}

	public int getLineNumber() {
		return m_lineNumber;
	}

	private void parseColumnInfo(@Nonnull VcfMetadata.Builder mdBuilder, @Nonnull String line) {
		mdBuilder.setColumns(Arrays.asList(sf_tabSplitter.split(line)));
	}

	public static class Builder {
		private BufferedReader m_reader;
		private Path m_vcfFile;
		private boolean m_rsidsOnly;
		private VcfLineParser m_vcfLineParser;

		/**
		 * Provides the {@link Path} to the VCF file to parse.
		 */
		public Builder fromFile(@Nonnull Path dataFile) {
			Preconditions.checkNotNull(dataFile);
			if (m_reader != null) {
				throw new IllegalStateException("Already loading from reader");
			}
			if (!dataFile.toString().endsWith(".vcf")) {
				throw new IllegalArgumentException("Not a VCF file (doesn't end with .vcf extension");
			}
			m_vcfFile = dataFile;
			return this;
		}

		/**
		 * Provides a {@link BufferedReader} to the beginning of the VCF file to parse.
		 */
		public Builder fromReader(@Nonnull BufferedReader reader) {
			Preconditions.checkNotNull(reader);
			if (m_vcfFile != null) {
				throw new IllegalStateException("Already loading from file");
			}
			m_reader = reader;
			return this;
		}

		/**
		 * Tells parser to ignore data lines that are not associated with an RSID.
		 */
		public Builder rsidsOnly() {
			m_rsidsOnly = true;
			return this;
		}

		public Builder parseWith(@Nonnull VcfLineParser lineParser) {
			Preconditions.checkNotNull(lineParser);
			m_vcfLineParser = lineParser;
			return this;
		}

		public VcfParser build() throws IOException {
			if (m_vcfLineParser == null) {
				throw new IllegalStateException("Missing VcfLineParser");
			}
			if (m_vcfFile != null) {
				m_reader = Files.newBufferedReader(m_vcfFile);
			}
			if (m_reader == null) {
				throw new IllegalStateException("Must specify either file or reader to parse");
			}
			return new VcfParser(m_reader, m_rsidsOnly, m_vcfLineParser);
		}
	}
}
