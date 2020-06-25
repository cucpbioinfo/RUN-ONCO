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

import run.onco.batch.api.service.VariantCallBatchService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration
public class VariantCallCron extends AbsCronImpl {

	private final static Logger logger = Logger.getLogger(VariantCallCron.class);

	@PostConstruct
	void init() {
		cronTriggerFactoryBean = variantCallCronTriggerFactoryBean();
		jobDetailFactoryBean = variantCallJobDetailFactory();
		register();
	}

	@Bean
	public JobDetailFactoryBean variantCallJobDetailFactory() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();

		jobDetailFactory.setJobClass(VariantCallJob.class);
		jobDetailFactory.setName("VariantCallCronJob");
		jobDetailFactory.setGroup("VariantCallJob");
		return jobDetailFactory;

	}

	@Bean
	public CronTriggerFactoryBean variantCallCronTriggerFactoryBean() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();

		cronTriggerFactoryBean.setJobDetail(variantCallJobDetailFactory().getObject());
		cronTriggerFactoryBean.setCronExpression("0 0/5 * * * ?");
		cronTriggerFactoryBean.setName("VariantCallCronTrigger");
		cronTriggerFactoryBean.setGroup("VariantCallJob");
		return cronTriggerFactoryBean;
	}

	public static class VariantCallJob implements Job {

		@Autowired
		public VariantCallBatchService variantCallBatchService;

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			try {
				logger.info("Running the Job ");
				variantCallBatchService.importVariantCallFiles();
			} catch (Exception e) {
				logger.error("Error when running the Job, the error is " + e.getMessage(), e);
			}
		}
	}
}
