package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.interceptor.CronjobInitialDefaultsInterceptor;
import com.beanframework.cronjob.interceptor.CronjobLoadInterceptor;
import com.beanframework.cronjob.interceptor.CronjobPrepareInterceptor;
import com.beanframework.cronjob.interceptor.CronjobRemoveInterceptor;
import com.beanframework.cronjob.interceptor.CronjobValidateInterceptor;

@Configuration
public class CronjobInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public CronjobInitialDefaultsInterceptor cronjobInitialDefaultsInterceptor() {
		return new CronjobInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobInitialDefaultsInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public CronjobLoadInterceptor cronjobLoadInterceptor() {
		return new CronjobLoadInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobLoadInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public CronjobPrepareInterceptor cronjobPrepareInterceptor() {
		return new CronjobPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobPrepareInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public CronjobValidateInterceptor cronjobValidateInterceptor() {
		return new CronjobValidateInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobValidateInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public CronjobRemoveInterceptor cronjobRemoveInterceptor() {
		return new CronjobRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobRemoveInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}
}
