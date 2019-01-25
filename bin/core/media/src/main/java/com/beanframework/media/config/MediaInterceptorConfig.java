package com.beanframework.media.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.media.domain.Media;
import com.beanframework.media.interceptor.MediaInitialDefaultsInterceptor;
import com.beanframework.media.interceptor.MediaInitializeInterceptor;
import com.beanframework.media.interceptor.MediaLoadInterceptor;
import com.beanframework.media.interceptor.MediaPrepareInterceptor;
import com.beanframework.media.interceptor.MediaRemoveInterceptor;
import com.beanframework.media.interceptor.MediaValidateInterceptor;

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

	////////////////////////////
	// Initialize Interceptor //
	////////////////////////////

	@Bean
	public MediaInitializeInterceptor mediaInitializeInterceptor() {
		return new MediaInitializeInterceptor();
	}

	@Bean
	public InterceptorMapping mediaInitializeInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(mediaInitializeInterceptor());
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
