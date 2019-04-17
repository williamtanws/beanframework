package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.dynamicfield.interceptor.template.DynamicFieldTemplateListLoadInterceptor;

@Configuration
public class DynamicFieldTemplateListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public DynamicFieldTemplateListLoadInterceptor dynamicFieldTemplateListLoadInterceptor() {
		return new DynamicFieldTemplateListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldTemplateListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldTemplateListLoadInterceptor());
		mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName() + "List");

		return mapping;
	}
}
