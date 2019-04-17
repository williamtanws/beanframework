package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.interceptor.DynamicFieldListLoadInterceptor;

@Configuration
public class DynamicFieldListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public DynamicFieldListLoadInterceptor dynamicFieldListLoadInterceptor() {
		return new DynamicFieldListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldListLoadInterceptor());
		mapping.setTypeCode(DynamicField.class.getSimpleName() + ModelService.DEFAULT_LIST_LOAD_INTERCEPTOR_POSTFIX);

		return mapping;
	}
}
