package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.notification.domain.Notification;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.notification.NotificationInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.notification.NotificationLoadInterceptor;
import com.beanframework.core.interceptor.notification.NotificationPrepareInterceptor;
import com.beanframework.core.interceptor.notification.NotificationRemoveInterceptor;
import com.beanframework.core.interceptor.notification.NotificationValidateInterceptor;

@Configuration
public class NotificationInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public NotificationInitialDefaultsInterceptor notificationInitialDefaultsInterceptor() {
		return new NotificationInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping notificationInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(notificationInitialDefaultsInterceptor());
		mapping.setTypeCode(Notification.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public NotificationLoadInterceptor notificationLoadInterceptor() {
		return new NotificationLoadInterceptor();
	}

	@Bean
	public InterceptorMapping notificationLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(notificationLoadInterceptor());
		mapping.setTypeCode(Notification.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public NotificationPrepareInterceptor notificationPrepareInterceptor() {
		return new NotificationPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping notificationPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(notificationPrepareInterceptor());
		mapping.setTypeCode(Notification.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public NotificationValidateInterceptor notificationValidateInterceptor() {
		return new NotificationValidateInterceptor();
	}

	@Bean
	public InterceptorMapping notificationValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(notificationValidateInterceptor());
		mapping.setTypeCode(Notification.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public NotificationRemoveInterceptor notificationRemoveInterceptor() {
		return new NotificationRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping notificationRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(notificationRemoveInterceptor());
		mapping.setTypeCode(Notification.class.getSimpleName());

		return mapping;
	}
}
