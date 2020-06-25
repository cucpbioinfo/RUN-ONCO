package run.onco.batch.api.service;

import org.quartz.JobDetail;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DynamicBatchService {

	public void executeJob(JobDetail jobDetail);
}
