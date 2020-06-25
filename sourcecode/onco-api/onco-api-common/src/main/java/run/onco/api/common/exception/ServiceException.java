package run.onco.api.common.exception;

import run.onco.api.common.message.ResponseStatus;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 401174389957266977L;

	public static final String NAMESPACE = "ONCO-SERVICE";

	protected Integer httpStatus;
	protected String errorCode;
	protected String errorDesc;
	protected String errorNamespace;
	protected String developerMessage;
	protected String channelId;

	public ServiceException() {

	}

	public ServiceException(String errorCode, String errorDesc) {
		this(new Integer(600), errorCode, errorDesc, NAMESPACE, null);
	}

	public ServiceException(Integer httpStatus, String errorCode, String errorDesc) {
		this(httpStatus, errorCode, errorDesc, NAMESPACE, null);
	}

	public ServiceException(ResponseStatus response) {
		this.httpStatus = new Integer(600);
		this.errorCode = response.getResponseCode();
		this.errorDesc = response.getResponseMessage();
		this.errorNamespace = response.getResponseNamespace();
		this.developerMessage = response.getDeveloperMessage();
	}

	public ServiceException(String errorCode, String errorDesc, String errorNamespace) {
		this.httpStatus = new Integer(600);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.errorNamespace = errorNamespace;
	}

	public ServiceException(String errorCode, Throwable cause) {
		super(cause);
		this.httpStatus = new Integer(600);
		this.errorCode = errorCode;
		this.errorDesc = cause.getMessage();
		this.errorNamespace = NAMESPACE;
	}

	public ServiceException(String errorCode, String errorNamespace, Throwable cause) {
		super(cause);
		this.httpStatus = new Integer(600);
		this.errorCode = errorCode;
		this.errorDesc = cause.getMessage();
		this.errorNamespace = errorNamespace;
	}

	public ServiceException(String errorCode, String errorDesc, String errorNamespace, String developerMessage) {
//		super(errorDesc + ":" + developerMessage);
		super(errorDesc);
		this.httpStatus = new Integer(600);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.errorNamespace = errorNamespace;
		this.developerMessage = developerMessage;
	}

	public ServiceException(Integer httpStatus, String errorCode, String errorDesc, String errorNamespace, String developerMessage) {
//		super(errorDesc + ":" + developerMessage);
		super(errorDesc);
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.errorNamespace = errorNamespace;
		this.developerMessage = developerMessage;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorNamespace() {
		return errorNamespace;
	}

	public void setErrorNamespace(String errorNamespace) {
		this.errorNamespace = errorNamespace;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
