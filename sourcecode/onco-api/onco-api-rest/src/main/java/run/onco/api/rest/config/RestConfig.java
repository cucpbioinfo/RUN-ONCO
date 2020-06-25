package run.onco.api.rest.config;

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

import run.onco.api.business.config.BusinessConfig;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.persist.config.JpaConfig;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = AppConstants.ROOT_PACKAGE)
@Import({ BusinessConfig.class, JpaConfig.class })
public class RestConfig {
	
	private final static Logger logger = Logger.getLogger(RestConfig.class);

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			logger.info("Boot enqueue application:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				logger.info(beanName);
			}

		};
	}
}