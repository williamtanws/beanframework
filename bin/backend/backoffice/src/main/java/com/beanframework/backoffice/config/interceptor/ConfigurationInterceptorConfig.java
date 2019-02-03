package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.configuration.interceptor.ConfigurationInitialDefaultsInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationInitializeInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationLoadInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationPrepareInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationRemoveInterceptor;
import com.beanframework.configuration.interceptor.ConfigurationValidateInterceptor;

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

	////////////////////////////
	// Initialize Interceptor //
	////////////////////////////

	@Bean
	public ConfigurationInitializeInterceptor configurationInitializeInterceptor() {
		return new ConfigurationInitializeInterceptor();
	}

	@Bean
	public InterceptorMapping configurationInitializeInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(configurationInitializeInterceptor());
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
