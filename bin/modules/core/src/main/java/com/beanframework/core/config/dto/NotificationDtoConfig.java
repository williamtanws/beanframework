package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.NotificationDtoConverter;
import com.beanframework.core.converter.populator.NotificationPopulator;
import com.beanframework.core.data.NotificationDto;

@Configuration
public class NotificationDtoConfig {

	@Autowired
	public NotificationPopulator notificationPopulator;

	@Bean
	public NotificationDtoConverter notificationDtoConverter() {
		NotificationDtoConverter converter = new NotificationDtoConverter();
		converter.addPopulator(notificationPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping notificationDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(notificationDtoConverter());
		mapping.setTypeCode(NotificationDto.class.getSimpleName());

		return mapping;
	}
}
