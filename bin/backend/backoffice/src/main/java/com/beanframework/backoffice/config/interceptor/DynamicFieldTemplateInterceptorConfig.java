package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.dynamicfield.interceptor.template.DynamicFieldTemplateInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.interceptor.template.DynamicFieldTemplateInitializeInterceptor;
import com.beanframework.dynamicfield.interceptor.template.DynamicFieldTemplateLoadInterceptor;
import com.beanframework.dynamicfield.interceptor.template.DynamicFieldTemplatePrepareInterceptor;
import com.beanframework.dynamicfield.interceptor.template.DynamicFieldTemplateRemoveInterceptor;
import com.beanframework.dynamicfield.interceptor.template.DynamicFieldTemplateValidateInterceptor;


@Configuration
public class DynamicFieldTemplateInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public DynamicFieldTemplateInitialDefaultsInterceptor dynamicFieldTemplateInitialDefaultsInterceptor() {
		return new DynamicFieldTemplateInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldTemplateInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldTemplateInitialDefaultsInterceptor());
		mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

		return mapping;
	}

	////////////////////////////
	// Initialize Interceptor //
	////////////////////////////

	@Bean
	public DynamicFieldTemplateInitializeInterceptor dynamicFieldTemplateInitializeInterceptor() {
		return new DynamicFieldTemplateInitializeInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldTemplateInitializeInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldTemplateInitializeInterceptor());
		mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public DynamicFieldTemplateLoadInterceptor dynamicFieldTemplateLoadInterceptor() {
		return new DynamicFieldTemplateLoadInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldTemplateLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldTemplateLoadInterceptor());
		mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public DynamicFieldTemplatePrepareInterceptor dynamicFieldTemplatePrepareInterceptor() {
		return new DynamicFieldTemplatePrepareInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldTemplatePrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldTemplatePrepareInterceptor());
		mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public DynamicFieldTemplateValidateInterceptor dynamicFieldTemplateValidateInterceptor() {
		return new DynamicFieldTemplateValidateInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldTemplateValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldTemplateValidateInterceptor());
		mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////
	
	@Bean
	public DynamicFieldTemplateRemoveInterceptor dynamicFieldTemplateRemoveInterceptor() {
		return new DynamicFieldTemplateRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldTemplateRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldTemplateRemoveInterceptor());
		mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

		return mapping;
	}
}
