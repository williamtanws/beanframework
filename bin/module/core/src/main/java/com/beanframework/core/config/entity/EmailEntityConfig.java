package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntityEmailConverter;
import com.beanframework.email.domain.Email;

@Configuration
public class EmailEntityConfig {

	@Bean
	public EntityEmailConverter entityEmailConverter() {
		return new EntityEmailConverter();
	}

	@Bean
	public ConverterMapping entityEmailConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityEmailConverter());
		mapping.setTypeCode(Email.class.getSimpleName());

		return mapping;
	}
}
