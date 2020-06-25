package run.onco.api.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class RestException {

	private HttpStatus status;
	private Integer code;
	private String message;
	private String developerMessage;
	private String moreInfoUrl;

	public RestException() {
	}

	public RestException(HttpStatus status, Integer code, String message, String developerMessage, String moreInfoUrl) {
		if (status == null) {
			throw new NullPointerException("HttpStatus argument cannot be null.");
		}
		this.status = status;
		this.code = code;
		this.message = message;
		this.developerMessage = developerMessage;
		this.moreInfoUrl = moreInfoUrl;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public String getMoreInfoUrl() {
		return moreInfoUrl;
	}

}
