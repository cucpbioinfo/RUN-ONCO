package run.onco.api.service;

import run.onco.api.persist.entity.TbLDownloadHistory;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DownloadLogService {

	public Long saveDownloadLog(TbLDownloadHistory downloadLog);
	
	public TbLDownloadHistory getDownloadLogById(final Long downloadLogId);
}
