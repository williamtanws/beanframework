package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.service.ModelService;
import com.beanframework.media.domain.Media;
import com.beanframework.media.interceptor.MediaListLoadInterceptor;

@Configuration
public class MediaListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public MediaListLoadInterceptor mediaListLoadInterceptor() {
		return new MediaListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping mediaListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(mediaListLoadInterceptor());
		mapping.setTypeCode(Media.class.getSimpleName() + ModelService.DEFAULT_LIST_LOAD_INTERCEPTOR_POSTFIX);

		return mapping;
	}
}
