package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.dynamicfield.DynamicFieldInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.dynamicfield.DynamicFieldLoadInterceptor;
import com.beanframework.core.interceptor.dynamicfield.DynamicFieldPrepareInterceptor;
import com.beanframework.core.interceptor.dynamicfield.DynamicFieldRemoveInterceptor;
import com.beanframework.core.interceptor.dynamicfield.DynamicFieldValidateInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

@Configuration
public class DynamicFieldInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public DynamicFieldInitialDefaultsInterceptor dynamicFieldInitialDefaultsInterceptor() {
		return new DynamicFieldInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldInitialDefaultsInterceptor());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public DynamicFieldLoadInterceptor dynamicFieldLoadInterceptor() {
		return new DynamicFieldLoadInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldLoadInterceptor());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public DynamicFieldPrepareInterceptor dynamicFieldPrepareInterceptor() {
		return new DynamicFieldPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldPrepareInterceptor());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public DynamicFieldValidateInterceptor dynamicFieldValidateInterceptor() {
		return new DynamicFieldValidateInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldValidateInterceptor());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public DynamicFieldRemoveInterceptor dynamicFieldRemoveInterceptor() {
		return new DynamicFieldRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldRemoveInterceptor());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}
}
