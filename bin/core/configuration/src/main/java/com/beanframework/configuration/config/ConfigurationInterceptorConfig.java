package com.beanframework.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.configuration.interceptor.ConfigurationInitialDefaultsInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationLoadInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationPrepareInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationRemoveInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationValidateInterceptor;

@Configuration
public class ConfigurationInterceptorConfig {
	@Bean
	public ConfigurationInitialDefaultsInterceptor configurationInitialDefaultsInterceptor() {
		return new ConfigurationInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping configurationInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(configurationInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Configuration.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public ConfigurationValidateInterceptor configurationValidateInterceptor() {
		return new ConfigurationValidateInterceptor();
	}

	@Bean
	public InterceptorMapping configurationValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(configurationValidateInterceptor());
		interceptorMapping.setTypeCode(Configuration.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public ConfigurationPrepareInterceptor configurationPrepareInterceptor() {
		return new ConfigurationPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping configurationPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(configurationPrepareInterceptor());
		interceptorMapping.setTypeCode(Configuration.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public ConfigurationLoadInterceptor configurationLoadInterceptor() {
		return new ConfigurationLoadInterceptor();
	}

	@Bean
	public InterceptorMapping configurationLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(configurationLoadInterceptor());
		interceptorMapping.setTypeCode(Configuration.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public ConfigurationRemoveInterceptor configurationRemoveInterceptor() {
		return new ConfigurationRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping configurationRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(configurationRemoveInterceptor());
		interceptorMapping.setTypeCode(Configuration.class.getSimpleName());

		return interceptorMapping;
	}
}
