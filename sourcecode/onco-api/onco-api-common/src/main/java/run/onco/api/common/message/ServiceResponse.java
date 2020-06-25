package run.onco.api.common.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 * @param <T>
 */
@JsonInclude(Include.NON_NULL)
public class ServiceResponse<T> {

	private Header header;
	private ResponseStatus responseStatus;

	private T data;
	
	public ServiceResponse() {
	}

	/**
	 * A Creates a new instance of Response
	 *
	 * @param responseStatus
	 */
	public ServiceResponse(final Header header, final ResponseStatus responseStatus) {
		this.header = header;
		this.responseStatus = responseStatus;
		this.data = null;
	}
	
	
	public Header getHeader() {
		return header;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	/**
	 * @return the response
	 */
	public T getData() {

		return this.data;
	}

	/**
	 * sets the response object
	 *
	 * @param obj
	 * @return
	 */
	public ServiceResponse<T> setData(final T obj) {

		this.data = obj;
		return this;
	}
}
