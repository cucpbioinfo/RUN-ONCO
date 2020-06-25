package run.onco.parser.vcf.model;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

/**
 * A VCF metadata element with an Id (called "ID").
 */
public class IdMetadata extends BaseMetadata {

	private final static Logger logger = Logger.getLogger(IdMetadata.class);

	public static final String ID = "ID";

	public IdMetadata(@Nonnull String id) {
		this(id, true);
	}

	public IdMetadata(@Nonnull Map<String, String> properties) {
		this(properties, true);
	}

	protected IdMetadata(@Nonnull String id, boolean isBaseType) {
		super(new HashMap<>());
		putPropertyRaw(ID, id);
		init(isBaseType);
	}

	protected IdMetadata(@Nonnull Map<String, String> properties, boolean isBaseType) {
		super(properties);
		init(isBaseType);
	}

	private void init(boolean isBaseType) {
		if (getPropertyRaw(ID) == null) {
			logger.warn(String.format("Required metadata property %s is missing", ID));
		}
		if (isBaseType) {
			ensureNoExtras(ID);
		}
	}

	/**
	 * @return Null only when incorrectly constructed without one
	 */
	@Nullable
	public String getId() {
		return getPropertyRaw(ID);
	}

}
