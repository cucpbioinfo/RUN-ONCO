package run.onco.api.common.message;

/**
 * 
 * @author Neda Peyrone
 *
 * @param <T>
 */
public class ServiceRequest<T> {

	private Header header;

	private T data;

	public ServiceRequest() {
	}

	/**
	 * A Creates a new instance of Request
	 *
	 * @param header
	 */
	public ServiceRequest(final Header header) {
		this.header = header;
		this.data = null;
	}

	public Header getHeader() {
		return header;
	}

	/**
	 * @return the request
	 */
	public T getData() {

		return this.data;
	}

	/**
	 * sets the request object
	 *
	 * @param obj
	 * @return
	 */
	public ServiceRequest<T> setData(final T obj) {

		this.data = obj;
		return this;
	}
}
