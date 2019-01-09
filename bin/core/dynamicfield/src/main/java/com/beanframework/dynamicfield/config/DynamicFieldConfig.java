package com.beanframework.dynamicfield.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.dynamicfield.converter.DtoDynamicFieldConverter;
import com.beanframework.dynamicfield.converter.EntityDynamicFieldConverter;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.interceptor.DynamicFieldInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldLoadInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldPrepareInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldRemoveInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldValidateInterceptor;

@Configuration
public class DynamicFieldConfig {

	///////////////////
	// DTO Converter //
	///////////////////

	@Bean
	public DtoDynamicFieldConverter dtoDynamicFieldConverter() {
		return new DtoDynamicFieldConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldConverter());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}
	
	//////////////////////
	// ENTITY Converter //
	//////////////////////

	@Bean
	public EntityDynamicFieldConverter entityDynamicFieldConverter() {
		return new EntityDynamicFieldConverter();
	}

	@Bean
	public ConverterMapping entityDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityDynamicFieldConverter());
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
