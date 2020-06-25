package run.onco.api.persist.dao;

import run.onco.api.persist.entity.TbLDownloadHistory;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DownloadLogDao {

	public TbLDownloadHistory getDownloadLogById(final Long downloadLogId);
}
