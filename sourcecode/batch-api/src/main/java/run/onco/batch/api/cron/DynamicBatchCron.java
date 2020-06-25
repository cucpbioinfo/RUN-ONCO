package run.onco.batch.api.cron;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import run.onco.api.common.utils.AppUtil;
import run.onco.api.persist.entity.TbCBatchJobInstance;
import run.onco.api.service.BatchJobService;
import run.onco.batch.api.service.DynamicBatchService;
import run.onco.batch.api.service.SchedulerService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration
public class DynamicBatchCron {
	
	private String jobKey;
	private String cronExpression;

	@Autowired
	private BatchJobService batchJobService;

	@Autowired
	private SchedulerService schedulerService;

	private final static Logger logger = Logger.getLogger(DynamicBatchCron.class);

	@PostConstruct
	void init() {
		List<TbCBatchJobInstance> jobInstances = batchJobService.getActiveBatchJobInstances();

		if (!AppUtil.isObjectEmpty(jobInstances)) {
			for (TbCBatchJobInstance instance : jobInstances) {
				jobKey = instance.getJobKey();
				// 0/30 0/1 * 1/1 * ? *
				cronExpression = instance.getCronExpression();
				
				JobDetail jobDetail = dynamicJobDetailFactory().getObject();
				CronTrigger cronTrigger = dynamicCronTriggerFactoryBean().getObject();
				schedulerService.register(jobDetail, cronTrigger);
				
				logger.info("Register trigger " + jobDetail.getKey() + " with schedule " + cronTrigger.getCronExpression());
			}
		}
	}

	@Bean
	public JobDetailFactoryBean dynamicJobDetailFactory() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();

		jobDetailFactory.setJobClass(DynamicJob.class);
		jobDetailFactory.setName(jobKey);
		jobDetailFactory.setGroup(jobKey + "Job");
		return jobDetailFactory;

	}

	@Bean
	public CronTriggerFactoryBean dynamicCronTriggerFactoryBean() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();

		cronTriggerFactoryBean.setJobDetail(dynamicJobDetailFactory().getObject());
		cronTriggerFactoryBean.setCronExpression(cronExpression);
		cronTriggerFactoryBean.setName(jobKey + "CronTrigger");
		cronTriggerFactoryBean.setGroup(jobKey + "Job");
		return cronTriggerFactoryBean;
	}

	public static class DynamicJob implements Job {

		@Autowired
		public DynamicBatchService dynamicBatchService;

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			try {
				logger.info("Running the Job ");
				JobDetail jobDetail = context.getJobDetail();
				dynamicBatchService.executeJob(jobDetail);
			} catch (Exception e) {
				logger.error("Error when running the Job, the error is " + e.getMessage(), e);
			}
		}
	}
}
