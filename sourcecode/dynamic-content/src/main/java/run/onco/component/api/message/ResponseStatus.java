package run.onco.component.api.message;

import java.io.Serializable;

/**
 * 
 * @author Neda Peyrone
 *
 */
	public class ResponseStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2899469370647803237L;

	private String responseCode;
	private String responseMessage;
	private String responseNamespace;
	private String developerMessage;
	
	public ResponseStatus() {		
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseNamespace() {
		return responseNamespace;
	}

	public void setResponseNamespace(String responseNamespace) {
		this.responseNamespace = responseNamespace;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
}