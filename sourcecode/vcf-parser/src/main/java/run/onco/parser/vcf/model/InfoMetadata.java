package run.onco.parser.vcf.model;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import run.onco.parser.vcf.VcfUtils;

/**
 * This class represents a single VCF INFO metadata line.
 * <p>
 * </p>
 * In 4.1:
 * 
 * <pre>
 * {@code
 * ##INFO=<ID=ID,Number=number,Type=type,Description="description">
 * }
 * </pre>
 * <p>
 * In 4.2:
 * 
 * <pre>
 * {@code
 * ##INFO=<ID=ID,Number=number,Type=type,Description="description",Source="source",Version="version">
 * }
 * </pre>
 *
 * @author Mark Woon
 */
public class InfoMetadata extends IdDescriptionMetadata {

	private final static Logger logger = Logger.getLogger(InfoMetadata.class);

	public static final String ID = "ID";
	public static final String DESCRIPTION = "Description"; // should be quoted
	public static final String NUMBER = "Number";
	public static final String TYPE = "Type";
	public static final String SOURCE = "Source"; // should be quoted
	public static final String VERSION = "Version"; // should be quoted

	private InfoType m_type;

	public InfoMetadata(@Nonnull String id, @Nonnull String description, @Nonnull InfoType type, @Nonnull String number,
			@Nullable String source, @Nullable String version) {
		super(id, description);
		putPropertyRaw(NUMBER, number);
		putPropertyRaw(TYPE, type.name());
		if (source != null) {
			putAndQuoteProperty(SOURCE, source);
		}
		if (version != null) {
			putAndQuoteProperty(VERSION, version);
		}
		init();
	}

	public InfoMetadata(@Nonnull Map<String, String> properties) {
		super(properties, false);
		init();
	}

	private void init() {
		String number = getPropertyRaw(NUMBER);
		assert number != null;
		if (!VcfUtils.NUMBER_PATTERN.matcher(number).matches()) {
			logger.warn(String.format("%s is not a number: %s", NUMBER, number));
		}
		m_type = InfoType.valueOf(getPropertyRaw(TYPE));
		ensureNoExtras(ID, DESCRIPTION, NUMBER, TYPE, SOURCE, VERSION);
	}

	/**
	 * Value is either an integer or "A", "G", "R", or ".".
	 * 
	 * @return Null only when incorrectly constructed without one
	 */
	@Nullable
	public String getNumber() {
		return getPropertyRaw(NUMBER);
	}

	/**
	 * @return A special (reserved) <em>Number</em> ("A", "G", "R", or "."), or null
	 *         if the Number is not reserved (it is numerical).
	 */
	@SuppressWarnings("ConstantConditions")
	@Nullable
	public SpecialVcfNumber getReservedNumber() {
		return SpecialVcfNumber.fromId(getPropertyRaw(NUMBER));
	}

	/**
	 * @return Null only when incorrectly constructed without one
	 */
	@Nullable
	public InfoType getType() {
		return m_type;
	}

	@Nullable
	public String getSource() {
		return getPropertyUnquoted(SOURCE);
	}

	@Nullable
	public String getVersion() {
		return getPropertyUnquoted(VERSION);
	}
}
