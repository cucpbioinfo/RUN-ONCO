package run.onco.api.mongo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import run.onco.api.mongo.constants.PropertiesConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class PropertiesConfiguration {
	private PropertiesConfiguration() {
		throw new IllegalStateException("class " + PropertiesConfiguration.class);
	}

	private static final Properties prop = new Properties();
	static {
		try {
			reloadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reloadConfig() throws IOException {
		InputStream inputStream = PropertiesConfiguration.class.getResourceAsStream(PropertiesConstants.PROPERTIES_PATH);
		prop.load(inputStream);
	}

	public static String get(String key) {
		return prop.getProperty(key);
	}
}
