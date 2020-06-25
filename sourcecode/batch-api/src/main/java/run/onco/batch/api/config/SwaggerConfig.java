package run.onco.batch.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration 
@EnableSwagger2
public class SwaggerConfig {
   @Bean
   public Docket customImplementation(){
      return new Docket(DocumentationType.SWAGGER_2)
    	  .select()
	      .apis(RequestHandlerSelectors.basePackage("run.onco.batch.api.controller"))
	      .paths(PathSelectors.any())
	      .build();
   }
}
