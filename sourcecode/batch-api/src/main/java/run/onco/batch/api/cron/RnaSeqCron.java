package run.onco.batch.api.cron;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import run.onco.batch.api.service.RnaSeqBatchService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration
public class RnaSeqCron extends AbsCronImpl {

	private final static Logger logger = Logger.getLogger(RnaSeqCron.class);

	@PostConstruct
	void init() {
		cronTriggerFactoryBean = rnaSeqCronTriggerFactoryBean();
		jobDetailFactoryBean = rnaSeqJobDetailFactory();
		register();
	}

	@Bean
	public JobDetailFactoryBean rnaSeqJobDetailFactory() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();

		jobDetailFactory.setJobClass(RnaSeqJob.class);
		jobDetailFactory.setName("RnaSeqCronJob");
		jobDetailFactory.setGroup("RnaSeqJob");
		return jobDetailFactory;

	}

	@Bean
	public CronTriggerFactoryBean rnaSeqCronTriggerFactoryBean() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();

		cronTriggerFactoryBean.setJobDetail(rnaSeqJobDetailFactory().getObject());
		cronTriggerFactoryBean.setCronExpression("0 0/15 * * * ?");
		cronTriggerFactoryBean.setName("RnaSeqCronTrigger");
		cronTriggerFactoryBean.setGroup("RnaSeqJob");
		return cronTriggerFactoryBean;
	}

	public static class RnaSeqJob implements Job {

		@Autowired
		public RnaSeqBatchService rnaSeqBatchService;

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			try {
				logger.info("Running the Job ");
				rnaSeqBatchService.importRnaSeqFiles();
			} catch (Exception e) {
				logger.error("Error when running the Job, the error is " + e.getMessage(), e);
			}
		}
	}
}
