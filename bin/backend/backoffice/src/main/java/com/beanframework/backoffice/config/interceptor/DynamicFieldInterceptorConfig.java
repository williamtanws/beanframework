package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.interceptor.DynamicFieldInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldInitializeInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldLoadInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldPrepareInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldRemoveInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldValidateInterceptor;


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

	////////////////////////////
	// Initialize Interceptor //
	////////////////////////////

	@Bean
	public DynamicFieldInitializeInterceptor dynamicFieldInitializeInterceptor() {
		return new DynamicFieldInitializeInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldInitializeInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldInitializeInterceptor());
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
