package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.interceptor.ConfigurationListLoadInterceptor;

@Configuration
public class ConfigurationListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public ConfigurationListLoadInterceptor configurationListLoadInterceptor() {
		return new ConfigurationListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping configurationListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(configurationListLoadInterceptor());
		mapping.setTypeCode(Configuration.class.getSimpleName() + ModelService.DEFAULT_LIST_LOAD_INTERCEPTOR_POSTFIX);

		return mapping;
	}
}
