package run.onco.parser.vcf.model;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import run.onco.parser.vcf.VcfUtils;

/**
 * This class represents a single VCF FORMAT metadata line.
 *
 * <pre>
 * {@code
 * ##FORMAT=<ID=ID,Number=number,Type=type,Description="description">
 * }
 * </pre>
 *
 * @author Mark Woon
 */
public class FormatMetadata extends IdDescriptionMetadata {

	private final static Logger logger = Logger.getLogger(ContigMetadata.class);

	public static final String ID = "ID";
	public static final String DESCRIPTION = "Description";
	public static final String NUMBER = "Number";
	public static final String TYPE = "Type";

	private FormatType m_type;

	public FormatMetadata(@Nonnull String id, @Nonnull String description, @Nonnull String number,
			@Nonnull FormatType type) {
		super(id, description, false);
		putPropertyRaw(NUMBER, number);
		putPropertyRaw(TYPE, type.name());
		init();
	}

	public FormatMetadata(@Nonnull Map<String, String> properties) {
		super(properties, false);
		init();
	}

	public void init() {
		String number = getPropertyRaw(NUMBER);
		if (number == null) {
			logger.warn(String.format("Required metadata property %s is missing", NUMBER));
		} else if (!VcfUtils.NUMBER_PATTERN.matcher(number).matches()) {
			logger.warn(String.format("%s is not a VCF number: %s", NUMBER, number));
		}
		m_type = FormatType.valueOf(getPropertyRaw(TYPE));
		ensureNoExtras(ID, DESCRIPTION, NUMBER, TYPE);
	}

	/**
	 * Value is either an integer or ".".
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
	public FormatType getType() {
		return m_type;
	}
}
