package com.beanframework.platform.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.beanframework.*.repository", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
@EnableTransactionManagement
@EnableAsync
@Order(0)
public class PlatformConfig {

	protected final Logger logger = LoggerFactory.getLogger(PlatformConfig.class);

	@Value("${platform.datasource.maxPoolSize:10}")
	private int PLATFORM_DATASOURCE_MAX_POOL_SIZE;

	@Value("${platform.import.sql.enabled:false}")
	private boolean PLATFORM_IMPORT_SQL_ENABLED;

	@Value("#{'${platform.import.sql.locations}'.split(',')}")
	private List<String> PLATFORM_IMPORT_SQL_LOCATIONS;

	@Value("${data.dir}")
	private String DIR_DATA;

	@Value("${temp.dir}")
	private String DIR_TEMP;

	@Value("${log.dir}")
	private String DIR_LOG;

	@Value("${platform.hibernate.hbm2ddl.auto}")
	private String PLATFORM_HIBERNATE_HDM2DDL_AUTO;

	@Value("${platform.hibernate.show_sql:false}")
	private String PLATFORM_HIBERNATE_SHOW_SQL;

	@Value("${platform.hibernate.format_sql:false}")
	private String PLATFORM_HIBERNATE_FORMAT_SQL;

	@Value("${platform.hibernate.dialect}")
	private String PLATFORM_HIBERNATE_DIALECT;

	@Value("${platform.hibernate.connection.release_mode:auto}")
	private String PLATFORM_HIBERNATE_CONNECTION_RELEASE_MODE;

	@Value("${platform.hibernate.connection.autoReconnect:true}")
	private String PLATFORM_HIBERNATE_CONNECTION_AUTORECONNECT;

	@Value("${platform.hibernate.c3p0.testWhileIdle:true}")
	private String PLATFORM_HIBERNATE_C3P0_TESTWHILTEIDLE;

	@Value("${platform.hibernate.c3p0.preferredTestQuery:SELECT 1}")
	private String PLATFORM_HIBERNATE_C3P0_PREFERREDTESTQUERY;

	@Value("${platform.hibernate.c3p0.autoCommitOnClose:true}")
	private String PLATFORM_HIBERNATE_C3P0_AUTOCOMMITONCLOSE;

	@Value("${platform.hibernate.c3p0.testConnectionOnCheckout:true}")
	private String PLATFORM_HIBERNATE_C3P0_TESTCONNECTIONONCHECKOUT;

	@Value("${platform.current_session_context_class:thread}")
	private String PLATFORM_CURRENT_SESSION_CONTEXT_CLASS;

	@Value("${platform.hibernate.envers.autoRegisterListeners:false}")
	private String PLATFORM_HIBERNATE_ENVERS_AUTOREGISTERLISTENERS;

	@Value("${platform.hibernate.cache.use_second_level_cache:true}")
	private String PLATFORM_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;

	@Value("${platform.hibernate.cache.region.factory_class:jcache}")
	private String PLATFORM_HIBERNATE_CACHE_REGION_FACTORY_CLASS;

	@Value("${platform.hibernate.javax.cache.provider:org.ehcache.jsr107.EhcacheCachingProvider}")
	private String PLATFORM_HIBERNATE_JAVAX_CACHE_PROVIDER;

	@Value("${platform.hibernate.javax.cache.uri:ehcache.xml}")
	private String PLATFORM_HIBERNATE_JAVAX_CACHE_URI;

	@Value("${platform.cache.default_cache_concurrency_strategy:READ_WRITE}")
	private String PLATFORM_HIBERNATE_DEFAULT_CACHE_CONCURRENCY_STRATEGRY;

	@Value("${platform.hibernate.javax.cache.missing_cache_strategy:false}")
	private String PLATFORM_HIBERNATE_JAVAX_CACHCE_MISSING_CACHE_STRATEGY;

	@Value("${platform.hibernate.cache.auto_evict_collection_cache:true}")
	private String PLATFORM_HIBERNATE_CACHE_AUTO_EVICT_COLLECTION_CACHE;

	@Value("${platform.javax.persistence.sharedCache.mode:ALL}")
	private String PLATFORM_JAVAX_PERSISTENCE_SHAREDCACHE_MODE;

	@Value("${platform.net.sf.ehcache.configurationResourceName:ehcache.xml}")
	private String PLATFORM_EHCACHE_CONFIGURATION;

	/*
	 * Populate SpringBoot DataSourceProperties object directly from application.yml
	 * based on prefix.Thanks to .yml, Hierachical data is mapped out of the box
	 * with matching-name properties of DataSourceProperties object].
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

		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create(dataSourceProperties.getClassLoader()).driverClassName(dataSourceProperties.getDriverClassName()).url(dataSourceProperties.getUrl())
				.username(dataSourceProperties.getUsername()).password(dataSourceProperties.getPassword()).type(HikariDataSource.class).build();
		dataSource.setMaximumPoolSize(PLATFORM_DATASOURCE_MAX_POOL_SIZE);

		if (PLATFORM_IMPORT_SQL_ENABLED) {
			for (String location : PLATFORM_IMPORT_SQL_LOCATIONS) {
				// schema init
				try {
					PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();

					Resource[] initSql = loader.getResources(location);

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
		}

		Files.createDirectories(Paths.get(DIR_DATA));
		Files.createDirectories(Paths.get(DIR_TEMP));
		Files.createDirectories(Paths.get(DIR_LOG));

		return dataSource;
	}

	@Value("${jpa.domain.packagetoscans}")
	private String[] jpadomainpackagetoscans;

	/*
	 * Entity Manager Factory setup.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException, IOException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(jpadomainpackagetoscans);
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

		properties.put("hibernate.connection.release_mode", PLATFORM_HIBERNATE_CONNECTION_RELEASE_MODE);
		properties.put("hibernate.connection.autoReconnect", PLATFORM_HIBERNATE_C3P0_TESTWHILTEIDLE);

		properties.put("hibernate.c3p0.testWhileIdle", PLATFORM_HIBERNATE_C3P0_TESTWHILTEIDLE);
		properties.put("hibernate.c3p0.preferredTestQuery", PLATFORM_HIBERNATE_C3P0_PREFERREDTESTQUERY);
		properties.put("hibernate.c3p0.autoCommitOnClose", PLATFORM_HIBERNATE_C3P0_AUTOCOMMITONCLOSE);
		properties.put("hibernate.c3p0.testConnectionOnCheckout", PLATFORM_HIBERNATE_C3P0_TESTCONNECTIONONCHECKOUT);

		properties.put("current_session_context_class", PLATFORM_CURRENT_SESSION_CONTEXT_CLASS);

		properties.put("hibernate.envers.autoRegisterListeners", PLATFORM_HIBERNATE_ENVERS_AUTOREGISTERLISTENERS);

		properties.put("hibernate.cache.use_second_level_cache", PLATFORM_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE);
		properties.put("hibernate.cache.use_query_cache", PLATFORM_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE);
		properties.put("hibernate.cache.region.factory_class", PLATFORM_HIBERNATE_CACHE_REGION_FACTORY_CLASS);
		properties.put("hibernate.cache.default_cache_concurrency_strategy", PLATFORM_HIBERNATE_DEFAULT_CACHE_CONCURRENCY_STRATEGRY);

		properties.put("hibernate.javax.cache.provider", PLATFORM_HIBERNATE_JAVAX_CACHE_PROVIDER);
		properties.put("hibernate.javax.cache.uri", PLATFORM_HIBERNATE_JAVAX_CACHE_URI);
		properties.put("hibernate.javax.cache.missing_cache_strategy", PLATFORM_HIBERNATE_JAVAX_CACHCE_MISSING_CACHE_STRATEGY);
		properties.put("hibernate.cache.auto_evict_collection_cache", PLATFORM_HIBERNATE_CACHE_AUTO_EVICT_COLLECTION_CACHE);

		properties.put("javax.persistence.sharedCache.mode", PLATFORM_JAVAX_PERSISTENCE_SHAREDCACHE_MODE);

		properties.put("net.sf.ehcache.configurationResourceName", PLATFORM_EHCACHE_CONFIGURATION);

		return properties;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
