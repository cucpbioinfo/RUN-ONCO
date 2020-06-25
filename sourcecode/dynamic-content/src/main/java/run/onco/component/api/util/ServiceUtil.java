package run.onco.component.api.util;

import run.onco.component.api.constants.AppConstants;
import run.onco.component.api.message.Header;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class ServiceUtil {
	
	private ServiceUtil() {
		throw new IllegalStateException("ServiceUtil class");
	}
	
	public static Header getHeader(final String serviceName) {
		Header header = new Header();
		header.setServiceName(serviceName);
		header.setReferenceNo(AppUtil.getRefNo());
		header.setTransactionDate(AppUtil.getTxnDate());
		header.setSystemCode(AppConstants.SYSTEM_CODE);
		header.setChannelId(AppConstants.CHANNEL_ID);
		return header;
	}
}
