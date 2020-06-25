package run.onco.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.BatchJobDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbCBatchJobInstance;
import run.onco.api.persist.entity.TbTBatchJob;
import run.onco.api.service.BatchJobService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class BatchJobServiceImpl implements BatchJobService {

	private final static Logger logger = Logger.getLogger(BatchJobServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private BatchJobDao batchJobDao;

	@Override
	public List<TbTBatchJob> listPaginatedBatchJobsUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Search BatchJob--:jobKey/%s", criteria.get("jobKey")));
			List<TbTBatchJob> batchJobs = batchJobDao.listPaginatedBatchJobsUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Search BatchJob--");
			return batchJobs;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search BatchJob--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countBatchJobsUsingQuery(Map<String, Object> criteria) {
		
		try {
			logger.info(String.format("I:--START--:--Get BatchJob PaginatedCount--:jobKey/%s", criteria.get("jobKey")));
			int count = batchJobDao.countBatchJobsUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get BatchJob PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get BatchJob PaginatedCount--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbCBatchJobInstance> getActiveBatchJobInstances() {
		
		try {
			logger.info("I:--START--:--Get ActiveBatchJobInstances--");
			List<TbCBatchJobInstance> jobInstances = batchJobDao.getActiveBatchJobInstances();
			logger.info("O:--SUCCESS--:--Get ActiveBatchJobInstances--");
			return jobInstances;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ActiveBatchJobInstances--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbCBatchJobInstance getBatchJobInstanceByJobKey(String jobKey) {
		
		try {
			logger.info(String.format("I:--START--:--Get BatchJobInstance by JobKey--:jobKey/%s", jobKey));
			Map<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("jobKey", jobKey);
			
			List<TbCBatchJobInstance> result = dao.findByCriteria(TbCBatchJobInstance.class, criteria);
			logger.info("O:--SUCCESS--:--Get BatchJobInstance by JobKey--");
			return result == null || result.size() == 0 ? null : result.get(0);
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get BatchJobInstance by JobKey--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
