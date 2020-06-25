package run.onco.api.rest.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.constants.ConfigurationConstants;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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

	private static final String GROUP_NAME = "OncoApi";
	
	@Autowired
	private Environment env;
	
	@Bean
	public Docket oncoApi() {
		return new Docket(DocumentationType.SWAGGER_2)  
        		.groupName(GROUP_NAME)
                .select()                                  
                .apis(RequestHandlerSelectors.basePackage(AppConstants.REST_API_PACKAGE))  
                .paths(PathSelectors.any())                          
                .build().apiInfo(metaData())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .protocols(protocols())
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
//                .securitySchemes(Arrays.asList(apiKey()));
	}
	
	private List<ResponseMessage> responseMessages() {
	    List<ResponseMessage> responseMessageList = new ArrayList<ResponseMessage>();
	    responseMessageList.add(createErrorResponseMessage(500, env.getProperty(ConfigurationConstants.HTTP_MESSAGE_ERROR_500)));
	    responseMessageList.add(createErrorResponseMessage(403, env.getProperty(ConfigurationConstants.HTTP_MESSAGE_ERROR_403)));
	    return responseMessageList;
	}
	
	private ResponseMessage createErrorResponseMessage(int code, String message) {
        return new ResponseMessageBuilder()
            .code(code)
            .message(message)
            .build();
    }

	@SuppressWarnings("rawtypes")
	private ApiInfo metaData() {
		List<VendorExtension> vendorExtensions= new ArrayList<>();
        return new ApiInfo(
        		env.getProperty(ConfigurationConstants.APP_TITLE), 
        		env.getProperty(ConfigurationConstants.APP_DESCRIPTION), 
        		env.getProperty(ConfigurationConstants.APP_VERSION), 
        		env.getProperty(ConfigurationConstants.APP_TERMS_OF_SERVICE_URL), 
        		new Contact(env.getProperty(ConfigurationConstants.APP_CONTACT_NAME), 
        					env.getProperty(ConfigurationConstants.APP_CONTACT_URL),
        					env.getProperty(ConfigurationConstants.APP_CONTACT_EMAIL)), 
        		env.getProperty(ConfigurationConstants.APP_LICENSE), 
        		env.getProperty(ConfigurationConstants.APP_LICENSE_URL),
        		vendorExtensions);
    }
	
	private ApiKey apiKey() {
	    return new ApiKey("token", "authorization", "header");
	}
	
	private Set<String> protocols() {
		Set<String> protocols = new HashSet<>();
		protocols.add("http");
		return protocols;
	}
	
	private List<? extends SecurityScheme> securitySchemes() {
        List<SecurityScheme> authorizationTypes = Arrays.asList(apiKey());
        return authorizationTypes;
    }
	
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts   = Arrays.asList(SecurityContext.builder().forPaths(PathSelectors.any()).securityReferences(securityReferences()).build());
		return securityContexts;
	}
	
	private List<SecurityReference> securityReferences() {
		List<SecurityReference> securityReferences = Arrays.asList(SecurityReference.builder().reference("token").scopes(new AuthorizationScope[0]).build());
		return securityReferences;
	}
}
