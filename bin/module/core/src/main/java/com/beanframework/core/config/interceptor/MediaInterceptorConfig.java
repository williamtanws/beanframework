package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.media.MediaInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.media.MediaLoadInterceptor;
import com.beanframework.core.interceptor.media.MediaPrepareInterceptor;
import com.beanframework.core.interceptor.media.MediaRemoveInterceptor;
import com.beanframework.core.interceptor.media.MediaValidateInterceptor;
import com.beanframework.media.domain.Media;

@Configuration
public class MediaInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public MediaInitialDefaultsInterceptor mediaInitialDefaultsInterceptor() {
		return new MediaInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping mediaInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(mediaInitialDefaultsInterceptor());
		mapping.setTypeCode(Media.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public MediaLoadInterceptor mediaLoadInterceptor() {
		return new MediaLoadInterceptor();
	}

	@Bean
	public InterceptorMapping mediaLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(mediaLoadInterceptor());
		mapping.setTypeCode(Media.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public MediaPrepareInterceptor mediaPrepareInterceptor() {
		return new MediaPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping mediaPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(mediaPrepareInterceptor());
		mapping.setTypeCode(Media.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public MediaValidateInterceptor mediaValidateInterceptor() {
		return new MediaValidateInterceptor();
	}

	@Bean
	public InterceptorMapping mediaValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(mediaValidateInterceptor());
		mapping.setTypeCode(Media.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public MediaRemoveInterceptor mediaRemoveInterceptor() {
		return new MediaRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping mediaRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(mediaRemoveInterceptor());
		mapping.setTypeCode(Media.class.getSimpleName());

		return mapping;
	}
}
