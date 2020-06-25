package run.onco.api.common.exception;

import run.onco.api.common.utils.MessageCode;

/**
 * 
 * @author Neda Peyrone
 *
 */
//@ResponseStatus( value = HttpStatus.PRECONDITION_FAILED )
public class ValidationException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 602297578417216660L;

	public ValidationException(MessageCode messageCode) {
		super(new Integer(412), messageCode.getCode(), messageCode.getDesc());
	}

	public ValidationException(String code, String desc) {
		super(new Integer(412), code, desc);
	}

	public ValidationException(String code, String desc, Throwable cause) {
		super(code, cause);
		setErrorDesc(desc);
	}
}
