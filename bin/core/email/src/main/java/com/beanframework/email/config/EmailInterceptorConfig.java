package com.beanframework.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.email.domain.Email;
import com.beanframework.email.interceptor.EmailInitialDefaultsInterceptor;
import com.beanframework.email.interceptor.EmailLoadInterceptor;
import com.beanframework.email.interceptor.EmailPrepareInterceptor;
import com.beanframework.email.interceptor.EmailRemoveInterceptor;
import com.beanframework.email.interceptor.EmailValidateInterceptor;

@Configuration
public class EmailInterceptorConfig {

	@Bean
	public EmailInitialDefaultsInterceptor EmailInitialDefaultsInterceptor() {
		return new EmailInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping EmailInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(EmailInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Email.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public EmailValidateInterceptor EmailValidateInterceptor() {
		return new EmailValidateInterceptor();
	}

	@Bean
	public InterceptorMapping EmailValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(EmailValidateInterceptor());
		interceptorMapping.setTypeCode(Email.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public EmailPrepareInterceptor EmailPrepareInterceptor() {
		return new EmailPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping EmailPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(EmailPrepareInterceptor());
		interceptorMapping.setTypeCode(Email.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public EmailLoadInterceptor EmailLoadInterceptor() {
		return new EmailLoadInterceptor();
	}

	@Bean
	public InterceptorMapping EmailLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(EmailLoadInterceptor());
		interceptorMapping.setTypeCode(Email.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public EmailRemoveInterceptor EmailRemoveInterceptor() {
		return new EmailRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping EmailRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(EmailRemoveInterceptor());
		interceptorMapping.setTypeCode(Email.class.getSimpleName());

		return interceptorMapping;
	}

}
