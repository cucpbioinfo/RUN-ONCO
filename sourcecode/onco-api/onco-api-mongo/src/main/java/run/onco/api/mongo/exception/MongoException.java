package run.onco.api.mongo.exception;

import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.utils.MessageCode;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class MongoException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2747975103652105442L;

	public MongoException(MessageCode messageCode) {
		super(new Integer(500), messageCode.getCode(), messageCode.getDesc());
	}

	public MongoException(String code, String desc) {
		super(new Integer(500), code, desc);
	}

	public MongoException(String code, String desc, Throwable cause) {
		super(code, cause);
		setErrorDesc(desc);
	}
}
