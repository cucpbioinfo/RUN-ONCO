package run.onco.api.persist.dao;

import java.util.Date;

import run.onco.api.persist.entity.TbTSequence;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface ConfigDao {

	public boolean checkServiceAuth(String systemCode, String channelId);

	public TbTSequence getCurrentSequence(String dataType, Date currentDate);

	public void saveNextSequence(TbTSequence sequence);

}
