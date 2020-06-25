package run.onco.batch.api.exception;

import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.utils.MessageCode;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class BatchException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3282186242435375602L;

	public BatchException(MessageCode messageCode) {
		super(new Integer(500), messageCode.getCode(), messageCode.getDesc());
	}

	public BatchException(String code, String desc) {
		super(new Integer(500), code, desc);
	}

	public BatchException(String code, String desc, Throwable cause) {
		super(code, cause);
		setErrorDesc(desc);
	}
}
