package com.beanframework.cronjob.config;

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

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.context.AutowiringSpringBeanJobFactory;
import com.beanframework.cronjob.converter.DtoCronjobConverter;
import com.beanframework.cronjob.converter.EntityCronjobConverter;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.interceptor.CronjobInitialDefaultsInterceptor;
import com.beanframework.cronjob.interceptor.CronjobLoadInterceptor;
import com.beanframework.cronjob.interceptor.CronjobPrepareInterceptor;
import com.beanframework.cronjob.interceptor.CronjobRemoveInterceptor;
import com.beanframework.cronjob.interceptor.CronjobValidateInterceptor;
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

		if(StringUtils.isNotEmpty(QUARTZ_PROPERTIES_LOCATION)) {
			FileSystemResource fileSystemResource = new FileSystemResource(QUARTZ_PROPERTIES_LOCATION);
			propertiesFactoryBean.setLocation(fileSystemResource);
		}
		
		if(StringUtils.isNotEmpty(QUARTZ_PROPERTIES_CLASSPATH)) {
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
	
	@Bean
	public DtoCronjobConverter dtoCronjobConverter() {
		return new DtoCronjobConverter();
	}
	
	@Bean
	public ConverterMapping dtoCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCronjobConverter());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	} 
	
	@Bean
	public EntityCronjobConverter entityCronjobConverter() {
		return new EntityCronjobConverter();
	}
	
	@Bean
	public ConverterMapping entityCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCronjobConverter());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	} 
	
	@Bean
	public CronjobInitialDefaultsInterceptor cronjobInitialDefaultsInterceptor() {
		return new CronjobInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public CronjobValidateInterceptor cronjobValidateInterceptor() {
		return new CronjobValidateInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobValidateInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public CronjobPrepareInterceptor cronjobPrepareInterceptor() {
		return new CronjobPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobPrepareInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public CronjobLoadInterceptor cronjobLoadInterceptor() {
		return new CronjobLoadInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobLoadInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public CronjobRemoveInterceptor cronjobRemoveInterceptor() {
		return new CronjobRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobRemoveInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());
		
		return interceptorMapping;
	}
}
