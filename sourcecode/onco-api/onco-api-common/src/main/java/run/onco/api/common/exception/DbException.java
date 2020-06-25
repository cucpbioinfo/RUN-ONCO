package run.onco.api.common.exception;

import run.onco.api.common.utils.MessageCode;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class DbException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1568271516901790223L;

	public DbException(MessageCode messageCode) {
		super(new Integer(500), messageCode.getCode(), messageCode.getDesc());
	}

	public DbException(String code, String desc) {
		super(new Integer(500), code, desc);
	}

	public DbException(String code, String desc, Throwable cause) {
		super(code, cause);
		setErrorDesc(desc);
	}
}
