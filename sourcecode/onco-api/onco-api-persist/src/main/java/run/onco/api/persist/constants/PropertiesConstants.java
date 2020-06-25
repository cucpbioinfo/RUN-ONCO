package run.onco.api.persist.constants;

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

	public static final String DATASOURCE_TYPE = "datasource.type";
	public static final String JNDI_DATASOURCE = "jndi.datasource.name";

	public static final String DATASOURCE_DRIVER = "datasource.driver";
	public static final String DATASOURCE_URL = "datasource.url";
	public static final String DATASOURCE_USERNAME = "datasource.username";
	public static final String DATASOURCE_PASSWORD = "datasource.password";

	public static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	public static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	public static final String HIBERNATE_DRIVER_CLASS = "hibernate.connection.driver_class";
	public static final String HIBERNATE_DIALECT = "hibernate.dialect";
	
	public static final String HIBERNATE_CHARSET = "hibernate.connection.CharSet";
	public static final String HIBERNATE_CHARACTER_ENCODING = "hibernate.connection.characterEncoding";
	public static final String HIBERNATE_USE_UNICODE = "hibernate.connection.useUnicode";

}