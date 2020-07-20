package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.configuration.ConfigurationInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.configuration.ConfigurationLoadInterceptor;
import com.beanframework.core.interceptor.configuration.ConfigurationPrepareInterceptor;
import com.beanframework.core.interceptor.configuration.ConfigurationRemoveInterceptor;
import com.beanframework.core.interceptor.configuration.ConfigurationValidateInterceptor;

@Configuration
public class ConfigurationInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public ConfigurationInitialDefaultsInterceptor configurationInitialDefaultsInterceptor() {
		return new ConfigurationInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping configurationInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(configurationInitialDefaultsInterceptor());
		mapping.setTypeCode(Configuration.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public ConfigurationLoadInterceptor configurationLoadInterceptor() {
		return new ConfigurationLoadInterceptor();
	}

	@Bean
	public InterceptorMapping configurationLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(configurationLoadInterceptor());
		mapping.setTypeCode(Configuration.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public ConfigurationPrepareInterceptor configurationPrepareInterceptor() {
		return new ConfigurationPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping configurationPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(configurationPrepareInterceptor());
		mapping.setTypeCode(Configuration.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public ConfigurationValidateInterceptor configurationValidateInterceptor() {
		return new ConfigurationValidateInterceptor();
	}

	@Bean
	public InterceptorMapping configurationValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(configurationValidateInterceptor());
		mapping.setTypeCode(Configuration.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public ConfigurationRemoveInterceptor configurationRemoveInterceptor() {
		return new ConfigurationRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping configurationRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(configurationRemoveInterceptor());
		mapping.setTypeCode(Configuration.class.getSimpleName());

		return mapping;
	}
}