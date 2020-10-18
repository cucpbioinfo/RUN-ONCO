package run.onco.api.common.utils;

/**
 * 
 * @author Neda Peyrone
 *
 */
public enum MessageCode {

	ERROR_INVALID_FORMAT("ONC-ERR001", "Incorrect format."),
	ERROR_INVALID_PARAM("ONC-ERR002", "Invalid parameter."),
	ERROR_DATA_NOT_FOUND("ONC-ERR003", "Data not found."),
	ERROR_INCORRECT_DATA("ONC-ERR004", "Incorrect data."),
	ERROR_DATABASE("ONC-ERR005", "A database error has occured."), 
	ERROR_DUPLICATED("ONC-ERR006", "Duplicate data exist."), 
	ERROR_INVALID_LOGIN("ONC-ERR007", "Invalid login."),
	ERROR_LIMIT_EXCEEDED("ONC-ERR009", "Failed limit exceeded."),
	ERROR_TOKEN_UUID_EXPIRED("ONC-ERR010", "Failed tokenUuid expired."),
	ERROR_DELETE_RECORD("ONC-ERR011", "Unable to delete the record which is referenced to another tables."),
	ERROR_SERVICE_FAIL_WS("ONC-UNK999","Call to external webservice fails."),
	ERROR_SERVICE_UNAVAIL("ONC-ERR999", "Service Unavailable."),
	ERROR_EXECUTE_R_FAIL("ONC-ERR011", "Rscript execution error."),
	ERROR_CLASS_NOT_FOUND("ONC-ERR012", "Class not found."),
	ERROR_EMPTY_FILE("ONC-ERR013", "File is empty."),
	SUCCESS("ONC-SCC000", "Service Success.");

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
