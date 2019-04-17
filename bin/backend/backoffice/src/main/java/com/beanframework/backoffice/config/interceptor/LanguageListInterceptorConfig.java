package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.language.domain.Language;
import com.beanframework.language.interceptor.LanguageListLoadInterceptor;

@Configuration
public class LanguageListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public LanguageListLoadInterceptor languageListLoadInterceptor() {
		return new LanguageListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping languageListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(languageListLoadInterceptor());
		mapping.setTypeCode(Language.class.getSimpleName() + "List");

		return mapping;
	}
}
