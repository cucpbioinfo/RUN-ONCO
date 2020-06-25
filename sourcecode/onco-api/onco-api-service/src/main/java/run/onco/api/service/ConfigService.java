package run.onco.api.service;

import java.util.Date;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface ConfigService {

	public boolean checkServiceAuth(String systemCode, String channelId);
	
	public Integer getCurrentSequence(String dataType, Date currentDate);

}
