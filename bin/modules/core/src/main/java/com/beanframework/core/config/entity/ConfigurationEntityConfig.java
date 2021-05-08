package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.ConfigurationEntityConverter;

@Configuration
public class ConfigurationEntityConfig {

	@Bean
	public ConfigurationEntityConverter entityConfigurationConverter() {
		return new ConfigurationEntityConverter();
	}

	@Bean
	public ConverterMapping entityConfigurationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityConfigurationConverter());
		mapping.setTypeCode(Configuration.class.getSimpleName());

		return mapping;
	}
}
