package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoConfigurationConverter;
import com.beanframework.core.data.ConfigurationDto;

@Configuration
public class ConfigurationDtoConfig {

	@Bean
	public DtoConfigurationConverter dtoConfigurationConverter() {
		return new DtoConfigurationConverter();
	}

	@Bean
	public ConverterMapping dtoConfigurationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoConfigurationConverter());
		mapping.setTypeCode(ConfigurationDto.class.getSimpleName());

		return mapping;
	}

}
