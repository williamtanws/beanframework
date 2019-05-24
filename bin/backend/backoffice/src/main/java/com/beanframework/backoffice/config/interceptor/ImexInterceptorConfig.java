package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.imex.domain.Imex;
import com.beanframework.imex.interceptor.ImexInitialDefaultsInterceptor;
import com.beanframework.imex.interceptor.ImexLoadInterceptor;
import com.beanframework.imex.interceptor.ImexPrepareInterceptor;
import com.beanframework.imex.interceptor.ImexRemoveInterceptor;
import com.beanframework.imex.interceptor.ImexValidateInterceptor;

@Configuration
public class ImexInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public ImexInitialDefaultsInterceptor imexInitialDefaultsInterceptor() {
		return new ImexInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping imexInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(imexInitialDefaultsInterceptor());
		mapping.setTypeCode(Imex.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public ImexLoadInterceptor imexLoadInterceptor() {
		return new ImexLoadInterceptor();
	}

	@Bean
	public InterceptorMapping imexLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(imexLoadInterceptor());
		mapping.setTypeCode(Imex.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public ImexPrepareInterceptor imexPrepareInterceptor() {
		return new ImexPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping imexPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(imexPrepareInterceptor());
		mapping.setTypeCode(Imex.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public ImexValidateInterceptor imexValidateInterceptor() {
		return new ImexValidateInterceptor();
	}

	@Bean
	public InterceptorMapping imexValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(imexValidateInterceptor());
		mapping.setTypeCode(Imex.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public ImexRemoveInterceptor imexRemoveInterceptor() {
		return new ImexRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping imexRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(imexRemoveInterceptor());
		mapping.setTypeCode(Imex.class.getSimpleName());

		return mapping;
	}
}
