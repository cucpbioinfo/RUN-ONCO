package run.onco.component.api.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import run.onco.component.api.service.DynamicContentService;
import run.onco.component.api.service.DynamicContentServiceImpl;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration
@ComponentScan(basePackages = "run.onco.component.api")
@PropertySource(value = { "classpath:application.properties" })
public class ApplicationConfiguration {
	
	@Bean(name = "dynamicContentService")
	public DynamicContentService dynamicContentService() {
		return new DynamicContentServiceImpl();
	}
}
