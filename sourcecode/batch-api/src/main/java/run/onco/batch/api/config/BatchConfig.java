package run.onco.batch.api.config;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import run.onco.api.persist.config.JpaConfig;
import run.onco.api.service.config.ServiceConfig;
import run.onco.batch.api.constants.BatchConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = BatchConstants.ROOT_PACKAGE)
@Import({ ServiceConfig.class, JpaConfig.class })
public class BatchConfig {
	private static Logger log = Logger.getLogger(BatchConfig.class);

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			log.info("Boot enqueue application:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				log.info(beanName);
			}

		};
	}
}