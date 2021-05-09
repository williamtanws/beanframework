package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.ConfigurationEntityConverter;

@Configuration
public class ConfigurationEntityConfig {

	@Autowired
	public ConfigurationEntityConverter configurationEntityConverter;

	@Bean
	public ConverterMapping configurationEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(configurationEntityConverter);
		mapping.setTypeCode(Configuration.class.getSimpleName());

		return mapping;
	}
}
