package run.onco.api.common.message;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.utils.MessageCode;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
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
	
	public ResponseStatus(MessageCode msgCode) {
		this.responseCode = msgCode.getCode();
		this.responseMessage = msgCode.getDesc();
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