package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityMediaConverter;
import com.beanframework.media.domain.Media;

@Configuration
public class MediaEntityConfig {

	@Bean
	public EntityMediaConverter entityMediaConverter() {
		return new EntityMediaConverter();
	}

	@Bean
	public ConverterMapping entityMediaConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityMediaConverter());
		mapping.setTypeCode(Media.class.getSimpleName());

		return mapping;
	}
}
