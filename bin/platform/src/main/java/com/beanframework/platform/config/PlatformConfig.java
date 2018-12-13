package com.beanframework.platform.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "${jpa.repository.basepackages}",
		entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager")
@EnableTransactionManagement
@EnableCaching
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PlatformConfig {
	
	protected final Logger logger = LoggerFactory.getLogger(PlatformConfig.class);

	@Value("${platform.datasource.maxPoolSize:10}")
	private int PLATFORM_DATASOURCE_MAX_POOL_SIZE;
			
	@Value("${platform.hibernate.hbm2ddl.auto}")
	private String PLATFORM_HIBERNATE_HDM2DDL_AUTO;
	
	@Value("${platform.hibernate.show_sql:false}")
	private boolean PLATFORM_HIBERNATE_SHOW_SQL;
	
	@Value("${platform.hibernate.format_sql:false}")
	private boolean PLATFORM_HIBERNATE_FORMAT_SQL;
	
	@Value("${platform.hibernate.dialect}")
	private String PLATFORM_HIBERNATE_DIALECT;
	
	@Value("${platform.import.startup}")
	private String PLATFORM_IMPORT_STARTUP;
	
	@Value("${platform.import.startup.enabled:false}")
	private boolean PLATFORM_IMPORT_STARTUP_ENABLED;
	
	@Value("${data.dir}")
	private String DIR_DATA;
	
	@Value("${temp.dir}")
	private String DIR_TEMP;
	
	@Value("${log.dir}")
	private String DIR_LOG;

	/*
	 * Populate SpringBoot DataSourceProperties object directly from application.yml 
	 * based on prefix.Thanks to .yml, Hierachical data is mapped out of the box with matching-name
	 * properties of DataSourceProperties object].
	 */
//	@Bean
//	@Primary
//	@ConfigurationProperties(prefix = "datasource.platform")
//	public DataSourceProperties dataSourceProperties(){
//		return new DataSourceProperties();
//	}
	
//	datasource.platform.url=
//			datasource.platform.username=
//			datasource.platform.password=
//			datasource.platform.driverClassName=
//			datasource.platform.defaultSchema=
	
	@Value("${datasource.platform.url}")
	private String DATASOURCE_URL;
	
	@Value("${datasource.platform.username}")
	private String DATASOURCE_USERNAME;
	
	@Value("${datasource.platform.password}")
	private String DATASOURCE_PASSWORD;
	
	@Value("${datasource.platform.driverClassName}")
	private String DATASOURCE_DRIVER_CLASS_NAME;

	/*
	 * Configure HikariCP pooled DataSource.
	 */
	@Bean
	public DataSource dataSource() throws IOException {
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setUrl(DATASOURCE_URL);
		dataSourceProperties.setUsername(DATASOURCE_USERNAME);
		dataSourceProperties.setPassword(DATASOURCE_PASSWORD);
		dataSourceProperties.setDriverClassName(DATASOURCE_DRIVER_CLASS_NAME);

		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
					.create(dataSourceProperties.getClassLoader())
					.driverClassName(dataSourceProperties.getDriverClassName())
					.url(dataSourceProperties.getUrl())
					.username(dataSourceProperties.getUsername())
					.password(dataSourceProperties.getPassword())
					.type(HikariDataSource.class)
					.build();
			dataSource.setMaximumPoolSize(PLATFORM_DATASOURCE_MAX_POOL_SIZE);
			
			if (PLATFORM_IMPORT_STARTUP_ENABLED) {
				// schema init
				try {
					PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();

					Resource[] initSql = loader.getResources(PLATFORM_IMPORT_STARTUP);

					ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
					for (Resource resource : initSql) {
						resourceDatabasePopulator.addScript(resource);
					}

					DatabasePopulator databasePopulator = resourceDatabasePopulator;
					DatabasePopulatorUtils.execute(databasePopulator, dataSource);
				} catch (DataAccessException e) {
					logger.warn(e.getMessage());
				} catch (IOException e) {
					logger.warn(e.getMessage());
				}
			}

			Files.createDirectories(Paths.get(DIR_DATA));
			Files.createDirectories(Paths.get(DIR_TEMP));
			Files.createDirectories(Paths.get(DIR_LOG));
						
			return dataSource;
	}
	
	@Value("${jpa.domain.packagetoscan}")
	private String[] jpadomainpackagetoscan;

	/*
	 * Entity Manager Factory setup.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException, IOException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(jpadomainpackagetoscan);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}

	/*
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", PLATFORM_HIBERNATE_HDM2DDL_AUTO);
		properties.put("hibernate.show_sql", PLATFORM_HIBERNATE_SHOW_SQL);
		properties.put("hibernate.format_sql", PLATFORM_HIBERNATE_FORMAT_SQL);
		properties.put("hibernate.dialect", PLATFORM_HIBERNATE_DIALECT);
		properties.put("hibernate.connection.release_mode", "auto");
		properties.put("hibernate.connection.autoReconnect", "true");
		properties.put("hibernate.c3p0.testWhileIdle", "true");
		properties.put("hibernate.c3p0.preferredTestQuery", "SELECT 1");
		properties.put("hibernate.c3p0.autoCommitOnClose", "true");
		properties.put("hibernate.c3p0.testConnectionOnCheckout", "true");
		properties.put("current_session_context_class", "thread");
		// Fix hibernate multiple merge problem
//		properties.put("hibernate.event.merge.entity_copy_observer", "allow");
		// Fix LAZY on session problems in unit tests
//		properties.put("hibernate.enable_lazy_load_no_trans", "true");
		return properties;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
