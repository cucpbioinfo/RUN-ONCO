package run.onco.api.persist.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import run.onco.api.common.config.ApplicationPasswordConfiguration;
import run.onco.api.persist.constants.DataSourceType;
import run.onco.api.persist.constants.PersistConstants;
import run.onco.api.persist.constants.PropertiesConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration
@ComponentScan(basePackages = PersistConstants.ROOT_PACKAGE)
@EnableJpaRepositories(PersistConstants.JPA_ENTITY_PACKAGE)
@EnableTransactionManagement
public class JpaConfig {

	@Autowired
	private Environment env;

	@Autowired
	private ApplicationPasswordConfiguration passwdConfig;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(PersistConstants.JPA_ENTITY_PACKAGE);

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Bean
	public DataSource dataSource() throws NamingException {
		if (DataSourceType.DIRECT == DataSourceType.valueOf(env.getProperty(PropertiesConstants.DATASOURCE_TYPE))) {
			return directDataSource();
		} else {
			return jndiDataSource();
		}
	}

	private DataSource jndiDataSource() throws NamingException {
		return (DataSource) new JndiTemplate().lookup(env.getProperty(PropertiesConstants.JNDI_DATASOURCE));

	}

	private DataSource directDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty(PropertiesConstants.DATASOURCE_DRIVER));
		dataSource.setUrl(env.getProperty(PropertiesConstants.DATASOURCE_URL));
		dataSource.setUsername(env.getProperty(PropertiesConstants.DATASOURCE_USERNAME));
		dataSource.setPassword(passwdConfig.getDatasourcePassword());
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty(PropertiesConstants.HIBERNATE_HBM2DDL_AUTO, env.getProperty(PropertiesConstants.HIBERNATE_HBM2DDL_AUTO));
		properties.setProperty(PropertiesConstants.HIBERNATE_SHOW_SQL, env.getProperty(PropertiesConstants.HIBERNATE_SHOW_SQL));
		properties.setProperty(PropertiesConstants.HIBERNATE_DIALECT, env.getProperty(PropertiesConstants.HIBERNATE_DIALECT));
		properties.setProperty(PropertiesConstants.HIBERNATE_CHARSET, env.getProperty(PropertiesConstants.HIBERNATE_CHARSET));
		properties.setProperty(PropertiesConstants.HIBERNATE_CHARACTER_ENCODING, env.getProperty(PropertiesConstants.HIBERNATE_CHARACTER_ENCODING));
		properties.setProperty(PropertiesConstants.HIBERNATE_USE_UNICODE, env.getProperty(PropertiesConstants.HIBERNATE_USE_UNICODE));
		
		if (DataSourceType.DIRECT == DataSourceType.valueOf(env.getProperty(PropertiesConstants.DATASOURCE_TYPE))) {
			properties.setProperty(PropertiesConstants.HIBERNATE_DRIVER_CLASS, env.getProperty(PropertiesConstants.HIBERNATE_DRIVER_CLASS));
		}

		return properties;

	}
}
