package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.enumuration.interceptor.EnumerationListLoadInterceptor;

@Configuration
public class EnumerationListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public EnumerationListLoadInterceptor enumerationListLoadInterceptor() {
		return new EnumerationListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping enumerationListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(enumerationListLoadInterceptor());
		mapping.setTypeCode(Enumeration.class.getSimpleName() + "List");

		return mapping;
	}
}
