package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.language.LanguageInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.language.LanguageLoadInterceptor;
import com.beanframework.core.interceptor.language.LanguagePrepareInterceptor;
import com.beanframework.core.interceptor.language.LanguageRemoveInterceptor;
import com.beanframework.core.interceptor.language.LanguageValidateInterceptor;
import com.beanframework.language.domain.Language;

@Configuration
public class LanguageInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public LanguageInitialDefaultsInterceptor languageInitialDefaultsInterceptor() {
		return new LanguageInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping languageInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(languageInitialDefaultsInterceptor());
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public LanguageLoadInterceptor languageLoadInterceptor() {
		return new LanguageLoadInterceptor();
	}

	@Bean
	public InterceptorMapping languageLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(languageLoadInterceptor());
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public LanguagePrepareInterceptor languagePrepareInterceptor() {
		return new LanguagePrepareInterceptor();
	}

	@Bean
	public InterceptorMapping languagePrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(languagePrepareInterceptor());
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public LanguageValidateInterceptor languageValidateInterceptor() {
		return new LanguageValidateInterceptor();
	}

	@Bean
	public InterceptorMapping languageValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(languageValidateInterceptor());
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public LanguageRemoveInterceptor languageRemoveInterceptor() {
		return new LanguageRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping languageRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(languageRemoveInterceptor());
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}
}
