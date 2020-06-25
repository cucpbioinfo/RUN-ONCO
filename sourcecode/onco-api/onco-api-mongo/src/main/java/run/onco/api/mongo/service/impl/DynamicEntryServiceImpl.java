package run.onco.api.mongo.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import run.onco.api.common.config.ApplicationPasswordConfiguration;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.mongo.constants.PropertiesConstants;
import run.onco.api.mongo.service.DynamicEntryService;
import run.onco.api.mongo.utils.MongoUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class DynamicEntryServiceImpl implements DynamicEntryService {
	
	private final static Logger logger = Logger.getLogger(DynamicEntryServiceImpl.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private ApplicationPasswordConfiguration passwdConfig;

	@Override
	public void addNewEntry() {
		try {
			MongoUtil.initMongoClient(getUri());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MongoUtil.close();
		}
	}
	
	private String getUri() throws Exception {
		String uri = env.getProperty(PropertiesConstants.MONGODB_URI);
		String username = env.getProperty(PropertiesConstants.MONGODB_USERNAME); 
		String password = passwdConfig.getMongoDbPassword();
		
		Pattern x = Pattern.compile(AppConstants.MONGODB_URI_PATTERN, Pattern.MULTILINE);
		Matcher m = x.matcher(uri);
		if (m.find()) {
			String newUri =  String.format("%s:%s@%s", username, password, m.group(1));
			return uri.replace(uri, newUri);
		}
		
		throw new Exception("XXXX");
	}
}
