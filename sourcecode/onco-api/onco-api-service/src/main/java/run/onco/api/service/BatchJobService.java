package run.onco.api.service;

import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbCBatchJobInstance;
import run.onco.api.persist.entity.TbTBatchJob;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface BatchJobService {

	public List<TbTBatchJob> listPaginatedBatchJobsUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public int countBatchJobsUsingQuery(Map<String, Object> criteria);
	
	public List<TbCBatchJobInstance> getActiveBatchJobInstances();
	
	public TbCBatchJobInstance getBatchJobInstanceByJobKey(final String jobKey);
}
