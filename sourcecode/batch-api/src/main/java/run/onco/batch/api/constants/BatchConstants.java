package run.onco.batch.api.constants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class BatchConstants {

	// root package of java code
	public static final String ROOT_PACKAGE = "run.onco.batch.api";
	public static final String UTIL_PACKAGE = ROOT_PACKAGE + ".util";
	
	public static final String BATCH_API_PACKAGE = "run.onco.batch.api.controller";

	public static final String CULTURE_SHORTNAME_THAI = "TH";
	public static final String CULTURE_SHORTNAME_ENGLISH = "EN";

	public static final String KNOWN_CULTURE_ENLISH_US = "en-US";
	public static final String KNOWN_CULTURE_THAI = "th-TH";

	public static final int SCALE_TWO_DIGIT = 2;

	public static final String DEFAULT_ENCODINIG = "UTF-8";

	private BatchConstants() {
		throw new IllegalStateException("BatchConstants class");
	}
}
