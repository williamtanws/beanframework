package com.beanframework.dynamicfield.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;
import com.beanframework.dynamicfield.interceptor.DynamicFieldInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldLoadInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldPrepareInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldRemoveInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldValidateInterceptor;
import com.beanframework.dynamicfield.interceptor.dynamicfieldenum.DynamicFieldEnumInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.interceptor.dynamicfieldenum.DynamicFieldEnumLoadInterceptor;
import com.beanframework.dynamicfield.interceptor.dynamicfieldenum.DynamicFieldEnumPrepareInterceptor;
import com.beanframework.dynamicfield.interceptor.dynamicfieldenum.DynamicFieldEnumRemoveInterceptor;
import com.beanframework.dynamicfield.interceptor.dynamicfieldenum.DynamicFieldEnumValidateInterceptor;

@Configuration
public class DynamicFieldInterceptorConfig {

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
	
	@Bean
	public DynamicFieldEnumLoadInterceptor dynamicFieldEnumLoadInterceptor() {
		return new DynamicFieldEnumLoadInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldEnumLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldEnumLoadInterceptor());
		mapping.setTypeCode(DynamicFieldEnum.class.getSimpleName());

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
	
	@Bean
	public DynamicFieldEnumInitialDefaultsInterceptor dynamicFieldEnumInitialDefaultsInterceptor() {
		return new DynamicFieldEnumInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldEnumInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldEnumInitialDefaultsInterceptor());
		mapping.setTypeCode(DynamicFieldEnum.class.getSimpleName());

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
	
	@Bean
	public DynamicFieldEnumPrepareInterceptor dynamicFieldEnumPrepareInterceptor() {
		return new DynamicFieldEnumPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldEnumPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldEnumPrepareInterceptor());
		mapping.setTypeCode(DynamicFieldEnum.class.getSimpleName());

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
	
	@Bean
	public DynamicFieldEnumValidateInterceptor dynamicFieldEnumValidateInterceptor() {
		return new DynamicFieldEnumValidateInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldEnumValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldEnumValidateInterceptor());
		mapping.setTypeCode(DynamicFieldEnum.class.getSimpleName());

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
	
	@Bean
	public DynamicFieldEnumRemoveInterceptor dynamicFieldEnumRemoveInterceptor() {
		return new DynamicFieldEnumRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldEnumRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldEnumRemoveInterceptor());
		mapping.setTypeCode(DynamicFieldEnum.class.getSimpleName());

		return mapping;
	}
}
