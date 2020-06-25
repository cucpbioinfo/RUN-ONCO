package run.onco.api.mongo.constants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class PropertiesConstants {
	private PropertiesConstants() {
		throw new IllegalStateException("class " + PropertiesConstants.class);
	}
	
	public static final String PROPERTIES_PATH = "/application.properties";
	
	public static final String MONGODB_URI = "mongodb.uri";
	public static final String MONGODB_USERNAME = "mongodb.username";
	public static final String MONGODB_PASSWORD = "mongodb.password";
}
