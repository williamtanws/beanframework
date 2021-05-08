package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.NotificationDtoConverter;
import com.beanframework.core.converter.populator.NotificationPopulator;
import com.beanframework.core.data.NotificationDto;

@Configuration
public class NotificationDtoConfig {

	@Bean
	public NotificationPopulator notificationPopulator() {
		return new NotificationPopulator();
	}

	@Bean
	public NotificationDtoConverter dtoNotificationConverter() {
		NotificationDtoConverter converter = new NotificationDtoConverter();
		converter.addPopulator(notificationPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoNotificationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoNotificationConverter());
		mapping.setTypeCode(NotificationDto.class.getSimpleName());

		return mapping;
	}
}
