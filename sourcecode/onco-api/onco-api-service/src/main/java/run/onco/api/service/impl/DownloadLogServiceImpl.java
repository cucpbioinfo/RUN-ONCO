package run.onco.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.DownloadLogDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbLDownloadHistory;
import run.onco.api.service.DownloadLogService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class DownloadLogServiceImpl implements DownloadLogService {
	
	private final static Logger logger = Logger.getLogger(DownloadLogServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private DownloadLogDao downloadLogDao;

	@Override
	@Transactional(readOnly = false)
	public Long saveDownloadLog(TbLDownloadHistory downloadLog) {
		
		try {
			logger.info("I:--START--:--Save DownloadLog--");
			dao.save(downloadLog);
			logger.info("O:--SUCCESS--:--Save DownloadLog");
			return downloadLog.getId();
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save DownloadLog--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbLDownloadHistory getDownloadLogById(Long downloadLogId) {
		
		try {
			logger.info(String.format("I:--START--:--Get DownloadLog by ID--:downloadLogId:/%s", downloadLogId));
			TbLDownloadHistory downloadHist = downloadLogDao.getDownloadLogById(downloadLogId);
			logger.info("O:--SUCCESS--:--Get DownloadLog by ID--");
			return downloadHist;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get DownloadLog--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
