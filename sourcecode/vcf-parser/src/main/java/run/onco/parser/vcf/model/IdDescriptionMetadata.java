package run.onco.parser.vcf.model;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

/**
 * This class represents a single VCF metadata line with an id and description.
 * <p>
 * In 4.1/4.2:
 * 
 * <pre>
 * {@code
 * ##ALT=<ID=type,Description="description">
 * ##FILTER=<ID=ID,Description="description">
 * }
 * </pre>
 *
 * @author Mark Woon
 */
public class IdDescriptionMetadata extends IdMetadata {

	private final static Logger logger = Logger.getLogger(IdDescriptionMetadata.class);

	public static final String ID = "ID";
	public static final String DESCRIPTION = "Description";

	public IdDescriptionMetadata(@Nonnull String id, @Nonnull String description) {
		this(id, description, true);
	}

	public IdDescriptionMetadata(@Nonnull Map<String, String> properties, boolean isBaseType) {
		super(properties, false);
		init(isBaseType);
	}

	protected IdDescriptionMetadata(@Nonnull String id, @Nonnull String description, boolean isBaseType) {
		super(id);
		putAndQuoteProperty(DESCRIPTION, description);
		init(isBaseType);
	}

	private void init(boolean isBaseType) {
		if (getPropertyRaw(DESCRIPTION) == null) {
			logger.warn(String.format("Required metadata property %s is missing", DESCRIPTION));
		}
		if (isBaseType) {
			ensureNoExtras(ID, DESCRIPTION);
		}
	}

	/**
	 * @return Null only when incorrectly constructed without one
	 */
	@Nullable
	public String getDescription() {
		return getPropertyUnquoted(DESCRIPTION);
	}

}
