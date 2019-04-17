package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.email.domain.Email;
import com.beanframework.email.interceptor.EmailListLoadInterceptor;

@Configuration
public class EmailListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public EmailListLoadInterceptor emailListLoadInterceptor() {
		return new EmailListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping emailListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(emailListLoadInterceptor());
		mapping.setTypeCode(Email.class.getSimpleName() + "List");

		return mapping;
	}
}
