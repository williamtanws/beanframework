package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntityConfigurationConverter;

@Configuration
public class ConfigurationEntityConfig {

	@Bean
	public EntityConfigurationConverter entityConfigurationConverter() {
		return new EntityConfigurationConverter();
	}

	@Bean
	public ConverterMapping entityConfigurationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityConfigurationConverter());
		mapping.setTypeCode(Configuration.class.getSimpleName());

		return mapping;
	}
}
