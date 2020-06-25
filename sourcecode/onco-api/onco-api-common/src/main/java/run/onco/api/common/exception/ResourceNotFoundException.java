package run.onco.api.common.exception;

import run.onco.api.common.utils.MessageCode;

/**
 * 
 * @author Neda Peyrone
 *
 */
//@ResponseStatus( value = HttpStatus.NOT_FOUND )
public class ResourceNotFoundException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4453913508509967127L;

	public ResourceNotFoundException(MessageCode messageCode) {
		super(new Integer(404), messageCode.getCode(), messageCode.getDesc());
	}

	public ResourceNotFoundException(String code, String desc) {
		super(new Integer(404), code, desc);
	}

	public ResourceNotFoundException(String code, String desc, Throwable cause) {
		super(code, cause);
		setErrorDesc(desc);
	}
}
