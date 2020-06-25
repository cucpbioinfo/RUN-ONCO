package run.onco.parser.vcf.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

/**
 * VCF metadata for contig=&lt;&gt; elements.
 * 
 * @author Douglas Myers-Turnbull
 */
public class ContigMetadata extends IdMetadata {

	private final static Logger logger = Logger.getLogger(ContigMetadata.class);

	public static final String ID = "ID";
	public static final String LENGTH = "length";
	public static final String ASSEMBLY = "assembly";
	public static final String MD5 = "md5";
	public static final String SPECIES = "species";
	public static final String TAXONOMY = "taxonomy";
	public static final String URL = "URL";

	public ContigMetadata(@Nonnull String id, long length, @Nonnull String assembly, @Nullable String md5,
			@Nullable String species, @Nullable String taxonomy, @Nullable String url) {
		super(id, false);
		putPropertyRaw(LENGTH, String.valueOf(length));
		putPropertyRaw(ASSEMBLY, assembly);
		if (md5 != null) {
			putPropertyRaw(MD5, md5);
		}
		if (species != null) {
			putAndQuoteProperty(SPECIES, species);
		}
		if (taxonomy != null) {
			putPropertyRaw(TAXONOMY, taxonomy);
		}
		if (url != null) {
			try {
				new URL(url);
			} catch (MalformedURLException e) {
				logger.warn(String.format("URL %s is malformed", url), e);
			}
		}
		putPropertyRaw(URL, url);
		init();
	}

	public ContigMetadata(@Nonnull Map<String, String> properties) {
		super(properties, false);
		init();
	}

	@SuppressWarnings("ConstantConditions")
	public long getLength() {
		return Long.parseLong(getPropertyRaw(LENGTH));
	}

	/**
	 * @return Null only when invalid
	 */
	@Nullable
	public String getAssembly() {
		return getPropertyRaw(ASSEMBLY);
	}

	@Nullable
	public String getTaxonomy() {
		return getPropertyRaw(TAXONOMY);
	}

	@Nullable
	public String getSpecies() {
		return getPropertyUnquoted(SPECIES);
	}

	@Nullable
	public String getMd5() {
		return getPropertyRaw(MD5);
	}

	@Nullable
	public String getUrl() {
		return getPropertyRaw(URL);
	}

	private void init() {
		if (getPropertyUnquoted(ASSEMBLY) == null) {
			logger.warn(String.format("Required metadata property %s is missing", ASSEMBLY));
		}
		String length = getPropertyUnquoted(LENGTH);
		if (length == null) {
			logger.warn(String.format("Required metadata property %s is missing", LENGTH));
		} else {
			try {
				// noinspection ResultOfMethodCallIgnored
				Long.parseLong(length);
			} catch (NumberFormatException e) {
				logger.warn("Length is not a number", e);
			}
		}
		ensureNoExtras(ID, LENGTH, ASSEMBLY, MD5, SPECIES, TAXONOMY, URL);
	}

}
