package run.onco.api.common.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import run.onco.api.common.config.ApplicationPasswordConfiguration;
import run.onco.api.common.message.Header;
import run.onco.api.common.message.ResponseStatus;
import run.onco.api.common.message.ServiceResponse;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Component
public class ServiceUtil {

	private final static Logger logger = Logger.getLogger(ServiceUtil.class);

	private static Environment env;
	private static ApplicationPasswordConfiguration passwdConfig;

	@Autowired(required = true)
	public void setEnvironment(Environment environment) {
		ServiceUtil.env = environment;
	}

	@Autowired(required = true)
	public void setPasswdConfig(ApplicationPasswordConfiguration config) {
		ServiceUtil.passwdConfig = config;
	}

	public static String getMappingCode(String responseCode) {
		logger.debug(String.format("I:--START--:--Get Mapping Code--:responseCode/%s", responseCode));
		String mappingCode = env.getProperty(responseCode);
		
		if(mappingCode == null) {
			mappingCode = MessageCode.ERROR_SERVICE_FAIL_WS.getCode();
		}
		
		logger.debug(String.format("O:--SUCCESS--:--Get Mapping Code--:mappingCode/%s", mappingCode));
		return mappingCode;
	}
	
	public static <T> ServiceResponse<T> buildResponse(Header header, MessageCode msgCode) {
		ResponseStatus responseStatus = new ResponseStatus(msgCode);
		return new ServiceResponse<T>(header, responseStatus);
	}
	
	public static <T> ServiceResponse<T> buildResponse(Header header, MessageCode msgCode, T data) {
		ResponseStatus responseStatus = new ResponseStatus(msgCode);
		ServiceResponse<T> response = new ServiceResponse<T>(header, responseStatus);
		response.setData(data);
		return response;
	}
	
	public static <T> ServiceResponse<T> buildResponse(Header header, String respCode, String respMsg) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setResponseCode(respCode);
		responseStatus.setResponseMessage(respMsg);
		return new ServiceResponse<T>(header, responseStatus);
	}

 }
