package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.interceptor.DynamicFieldInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldLoadInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldPrepareInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldRemoveInterceptor;
import com.beanframework.dynamicfield.interceptor.DynamicFieldValidateInterceptor;
import com.beanframework.dynamicfield.interceptor.relationship.DynamicFieldEnumerationRelationshipRemoveInterceptor;
import com.beanframework.dynamicfield.interceptor.relationship.DynamicFieldLanguageRelationshipRemoveInterceptor;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.language.domain.Language;

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

	/////////////////////////////
	// Enumeration Interceptor //
	/////////////////////////////

	@Bean
	public DynamicFieldEnumerationRelationshipRemoveInterceptor dynamicFieldEnumerationRelationshipRemoveInterceptor() {
		return new DynamicFieldEnumerationRelationshipRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldEnumerationRelationshipRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldEnumerationRelationshipRemoveInterceptor());
		mapping.setTypeCode(Enumeration.class.getSimpleName());
		mapping.setOrder(Integer.MIN_VALUE);

		return mapping;
	}

	//////////////////////////
	// Language Interceptor //
	//////////////////////////

	@Bean
	public DynamicFieldLanguageRelationshipRemoveInterceptor dynamicFieldLanguageRelationshipRemoveInterceptor() {
		return new DynamicFieldLanguageRelationshipRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldLanguageRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldLanguageRelationshipRemoveInterceptor());
		mapping.setTypeCode(Language.class.getSimpleName());
		mapping.setOrder(Integer.MIN_VALUE);

		return mapping;
	}
}
