package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.enumuration.EnumerationInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.enumuration.EnumerationLoadInterceptor;
import com.beanframework.core.interceptor.enumuration.EnumerationPrepareInterceptor;
import com.beanframework.core.interceptor.enumuration.EnumerationRemoveInterceptor;
import com.beanframework.core.interceptor.enumuration.EnumerationValidateInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

@Configuration
public class EnumerationInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public EnumerationInitialDefaultsInterceptor enumerationInitialDefaultsInterceptor() {
		return new EnumerationInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping enumerationInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(enumerationInitialDefaultsInterceptor());
		mapping.setTypeCode(Enumeration.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public EnumerationLoadInterceptor enumerationLoadInterceptor() {
		return new EnumerationLoadInterceptor();
	}

	@Bean
	public InterceptorMapping enumerationLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(enumerationLoadInterceptor());
		mapping.setTypeCode(Enumeration.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public EnumerationPrepareInterceptor enumerationPrepareInterceptor() {
		return new EnumerationPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping enumerationPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(enumerationPrepareInterceptor());
		mapping.setTypeCode(Enumeration.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public EnumerationValidateInterceptor enumerationValidateInterceptor() {
		return new EnumerationValidateInterceptor();
	}

	@Bean
	public InterceptorMapping enumerationValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(enumerationValidateInterceptor());
		mapping.setTypeCode(Enumeration.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public EnumerationRemoveInterceptor enumerationRemoveInterceptor() {
		return new EnumerationRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping enumerationRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(enumerationRemoveInterceptor());
		mapping.setTypeCode(Enumeration.class.getSimpleName());

		return mapping;
	}
}
