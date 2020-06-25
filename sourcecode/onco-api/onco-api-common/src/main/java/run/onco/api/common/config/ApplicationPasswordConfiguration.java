package run.onco.api.common.config;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Component
@Configuration
public class ApplicationPasswordConfiguration {

	private final static Logger logger = Logger.getLogger(ApplicationPasswordConfiguration.class);

	private static StandardPBEStringEncryptor encryptor;
	private static final String PBE_ALGORITHM = "PBEWithMD5AndDES";
	
	private static Environment env;
	
	@Autowired(required = true)
	public void setEnvironment(Environment environment) {
		ApplicationPasswordConfiguration.env = environment;
	}

	public String getKycPassword() {
		String msgKey = env.getProperty("ekyc.password");
		logger.debug(String.format("I:--START--:--Get KycPassword--:msgKey/%s", msgKey));
		
		String passwd = encryptor.decrypt(msgKey);
		logger.debug(String.format("O:--SUCCESS--:--Get KycPassword--:msgResult/%s", passwd));
		return passwd;
	}

	public String getCisPassword() {
		String msgKey = env.getProperty("cis.password");		
		logger.debug(String.format("I:--START--:--Get CisPassword--:msgKey/%s", msgKey));
		
		String passwd = encryptor.decrypt(msgKey);
		logger.debug(String.format("O:--SUCCESS--:--Get CisPassword--:msgResult/%s", passwd));
		return passwd;
	}
	
	public String getDatasourcePassword() {
		String msgKey = env.getProperty("datasource.password");
		logger.debug(String.format("I:--START--:--Get DatasourcePassword--:msgKey/%s", msgKey));
		
		String passwd = encryptor.decrypt(msgKey);
		logger.debug(String.format("O:--SUCCESS--:--Get DatasourcePassword--:msgResult/%s", passwd));
		return passwd;
	}
	
	public String getMongoDbPassword() {
		String msgKey = env.getProperty("mongodb.password");
		logger.debug(String.format("I:--START--:--Get MongoDbPassword--:msgKey/%s", msgKey));
		
		String passwd = encryptor.decrypt(msgKey);
		logger.debug(String.format("O:--SUCCESS--:--Get MongoDbPassword--:msgResult/%s", passwd));
		return passwd;
	}

	@PostConstruct
	public static void stringEncryptor() {
		logger.info("I:--START--:--New StringEncryptor--");
		encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm(PBE_ALGORITHM);
		encryptor.setPassword(env.getProperty("app.encryption.password"));
		logger.info("O:--SUCCESS--:--New StringEncryptor--");
	}
}
