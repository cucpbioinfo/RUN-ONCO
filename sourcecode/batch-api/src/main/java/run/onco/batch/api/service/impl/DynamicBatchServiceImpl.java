package run.onco.batch.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.persist.entity.TbCBatchJobInstance;
import run.onco.api.persist.entity.TbTBatchJob;
import run.onco.api.service.BatchJobService;
import run.onco.batch.api.dto.BatchJobDto;
import run.onco.batch.api.service.DynamicBatchService;
import run.onco.batch.api.utils.Task;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class DynamicBatchServiceImpl implements DynamicBatchService {
	
	private final static Logger logger = Logger.getLogger(DynamicBatchServiceImpl.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private BatchJobService batchJobService;
	
	private String getString(String key) {
		return env.getProperty(key);
	}

	@Override
	public void executeJob(JobDetail jobDetail) {
		
		logger.info("I:--START--:--Call Msisensor API--");
		
		if (!AppUtil.isObjectEmpty(jobDetail)) {
			String jobKey = jobDetail.getKey().getName();
			TbCBatchJobInstance jobInstance = batchJobService.getBatchJobInstanceByJobKey(jobKey);
			
			if (!AppUtil.isObjectEmpty(jobInstance)) {
				int startIndex = 0;
				int fetchSize = 2;
				
				HashMap<String, Object> criteria = new HashMap<String, Object>();
				criteria.put("jobKey", jobKey);
				criteria.put("status", AppConstants.STATUS_DRAFT);
				
				Paging paging = new Paging();
				paging.setStartIndex(startIndex);
				paging.setFetchSize(fetchSize);
				
				int totalRecords = batchJobService.countBatchJobsUsingQuery(criteria);
				
				if (totalRecords > 0) {

					List<TbTBatchJob> batchJobs = batchJobService.listPaginatedBatchJobsUsingQuery(criteria, paging);
					
					logger.debug(String.format("O:--Get BatchJobs--:batchJobs size/%s", !AppUtil.isObjectEmpty(batchJobs) ? batchJobs.size() : 0));

					ExecutorService executor = Executors.newFixedThreadPool(10);
					
					batchJobs.stream().forEach(x -> {
						BatchJobDto batchJobDto = new BatchJobDto();
						batchJobDto.setIncomingDir(jobInstance.getIncomingDir());
						batchJobDto.setOutgoingDir(jobInstance.getOutgoingDir());
						batchJobDto.setJobKey(jobInstance.getJobKey());
						batchJobDto.setJobName(jobInstance.getJobName());
						batchJobDto.setDataDir(x.getDataDir());
						
						Map<String, String> params = new HashMap<>();
						x.getBatchJobParams().forEach(p -> {
							params.put(p.getAttrName(), p.getAttrValue());
						});
						
						batchJobDto.setParams(params);
						
						RestTemplate restTemplate = new RestTemplate();
						executor.submit(new Task(restTemplate, jobInstance.getEndpointUrl(), batchJobDto));
					});
				}
			}
		}
	}
}
