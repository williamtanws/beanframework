package com.beanframework.backoffice.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.context.AutowiringSpringBeanJobFactory;
import com.beanframework.cronjob.listener.CronjobGlobalListener;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class CronjobConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Value(CronjobConstants.QUARTZ_PROPERTIES_LOCATION)
	private String QUARTZ_PROPERTIES_LOCATION;

	@Value(CronjobConstants.QUARTZ_PROPERTIES_CLASSPATH)
	private String QUARTZ_PROPERTIES_CLASSPATH;

	@Bean
	public Properties quartzProperties() throws Exception {

		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();

		if (StringUtils.isNotBlank(QUARTZ_PROPERTIES_LOCATION)) {
			FileSystemResource fileSystemResource = new FileSystemResource(QUARTZ_PROPERTIES_LOCATION);
			propertiesFactoryBean.setLocation(fileSystemResource);
		}

		if (StringUtils.isNotBlank(QUARTZ_PROPERTIES_CLASSPATH)) {
			propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_CLASSPATH));
		}

		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public CronjobGlobalListener globalJobListener() {
		return new CronjobGlobalListener();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactory(PlatformTransactionManager transactionManager) throws Exception {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setQuartzProperties(quartzProperties());
		schedulerFactory.setOverwriteExistingJobs(true);

		// Custom job factory of spring with DI support for @Autowired
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		schedulerFactory.setJobFactory(jobFactory);
		schedulerFactory.setGlobalJobListeners(globalJobListener());
		schedulerFactory.setTransactionManager(transactionManager);
		schedulerFactory.setAutoStartup(true);

		return schedulerFactory;
	}

}
