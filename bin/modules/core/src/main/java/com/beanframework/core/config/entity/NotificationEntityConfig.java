package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.NotificationEntityConverter;
import com.beanframework.notification.domain.Notification;

@Configuration
public class NotificationEntityConfig {

	@Bean
	public NotificationEntityConverter entityNotificationConverter() {
		return new NotificationEntityConverter();
	}

	@Bean
	public ConverterMapping entityNotificationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityNotificationConverter());
		mapping.setTypeCode(Notification.class.getSimpleName());

		return mapping;
	}
}
