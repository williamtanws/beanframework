package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.TemplateInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.TemplateLoadInterceptor;
import com.beanframework.core.interceptor.TemplatePrepareInterceptor;
import com.beanframework.core.interceptor.TemplateRemoveInterceptor;
import com.beanframework.core.interceptor.TemplateValidateInterceptor;
import com.beanframework.template.domain.Template;

@Configuration
public class TemplateInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public TemplateInitialDefaultsInterceptor templateInitialDefaultsInterceptor() {
		return new TemplateInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping templateInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(templateInitialDefaultsInterceptor());
		mapping.setTypeCode(Template.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public TemplateLoadInterceptor templateLoadInterceptor() {
		return new TemplateLoadInterceptor();
	}

	@Bean
	public InterceptorMapping templateLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(templateLoadInterceptor());
		mapping.setTypeCode(Template.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public TemplatePrepareInterceptor templatePrepareInterceptor() {
		return new TemplatePrepareInterceptor();
	}

	@Bean
	public InterceptorMapping templatePrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(templatePrepareInterceptor());
		mapping.setTypeCode(Template.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public TemplateValidateInterceptor templateValidateInterceptor() {
		return new TemplateValidateInterceptor();
	}

	@Bean
	public InterceptorMapping templateValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(templateValidateInterceptor());
		mapping.setTypeCode(Template.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public TemplateRemoveInterceptor templateRemoveInterceptor() {
		return new TemplateRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping templateRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(templateRemoveInterceptor());
		mapping.setTypeCode(Template.class.getSimpleName());

		return mapping;
	}
}
