package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.language.domain.Language;
import com.beanframework.language.interceptor.LanguageInitialDefaultsInterceptor;
import com.beanframework.language.interceptor.LanguageInitializeInterceptor;
import com.beanframework.language.interceptor.LanguageLoadInterceptor;
import com.beanframework.language.interceptor.LanguagePrepareInterceptor;
import com.beanframework.language.interceptor.LanguageRemoveInterceptor;
import com.beanframework.language.interceptor.LanguageValidateInterceptor;

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

	////////////////////////////
	// Initialize Interceptor //
	////////////////////////////

	@Bean
	public LanguageInitializeInterceptor languageInitializeInterceptor() {
		return new LanguageInitializeInterceptor();
	}

	@Bean
	public InterceptorMapping languageInitializeInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(languageInitializeInterceptor());
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
