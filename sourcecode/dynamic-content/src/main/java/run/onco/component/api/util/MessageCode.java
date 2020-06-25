package run.onco.component.api.util;

/**
 * 
 * @author Neda Peyrone
 *
 */
public enum MessageCode {

	SUCCESS("ONC-SCC000", "Service Success."),
	ERROR_CALL_EXTERNAL_API("ONC-UNK999","Call to external API fails.");

	private final String code;
	private final String desc;

	private MessageCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + desc;
	}
}
