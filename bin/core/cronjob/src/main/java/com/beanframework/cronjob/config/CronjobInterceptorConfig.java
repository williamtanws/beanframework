package com.beanframework.cronjob.config;

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
	@Bean
	public CronjobInitialDefaultsInterceptor cronjobInitialDefaultsInterceptor() {
		return new CronjobInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public CronjobValidateInterceptor cronjobValidateInterceptor() {
		return new CronjobValidateInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobValidateInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public CronjobPrepareInterceptor cronjobPrepareInterceptor() {
		return new CronjobPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobPrepareInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public CronjobLoadInterceptor cronjobLoadInterceptor() {
		return new CronjobLoadInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobLoadInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public CronjobRemoveInterceptor cronjobRemoveInterceptor() {
		return new CronjobRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(cronjobRemoveInterceptor());
		interceptorMapping.setTypeCode(Cronjob.class.getSimpleName());

		return interceptorMapping;
	}
}
