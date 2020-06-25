package run.onco.component.api.exception;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7011047493881133772L;

	public ServiceException() {
		super();
	}

	public ServiceException(String s) {
		super(s);
	}

	public ServiceException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ServiceException(Throwable throwable) {
		super(throwable);
	}
}
