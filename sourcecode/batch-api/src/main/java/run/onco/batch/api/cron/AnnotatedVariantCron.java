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
public class AnnotatedVariantCron extends AbsCronImpl {

	private final static Logger logger = Logger.getLogger(AnnotatedVariantCron.class);

	@PostConstruct
	void init() {
		cronTriggerFactoryBean = annotatedVariantCronTriggerFactoryBean();
		jobDetailFactoryBean = annotatedVariantJobDetailFactory();
		register();
	}

	@Bean
	public JobDetailFactoryBean annotatedVariantJobDetailFactory() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();

		jobDetailFactory.setJobClass(AnnotatedVariantJob.class);
		jobDetailFactory.setName("AnnotatedVariantCronJob");
		jobDetailFactory.setGroup("AnnotatedVariantJob");
		return jobDetailFactory;

	}

	@Bean
	public CronTriggerFactoryBean annotatedVariantCronTriggerFactoryBean() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();

		cronTriggerFactoryBean.setJobDetail(annotatedVariantJobDetailFactory().getObject());
		cronTriggerFactoryBean.setCronExpression("0 0/1 * * * ?");
		cronTriggerFactoryBean.setName("AnnotatedVariantCronTrigger");
		cronTriggerFactoryBean.setGroup("AnnotatedVariantJob");
		return cronTriggerFactoryBean;
	}

	public static class AnnotatedVariantJob implements Job {

		@Autowired
		public VariantCallBatchService variantCallBatchService;

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			try {
				logger.info("Running the Job ");
				variantCallBatchService.annotateVariant();
			} catch (Exception e) {
				logger.error("Error when running the Job, the error is " + e.getMessage(), e);
			}
		}
	}
}
