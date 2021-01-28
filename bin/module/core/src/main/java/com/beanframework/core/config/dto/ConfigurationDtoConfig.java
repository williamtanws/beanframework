package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoConfigurationConverter;
import com.beanframework.core.converter.populator.ConfigurationPopulator;
import com.beanframework.core.data.ConfigurationDto;

@Configuration
public class ConfigurationDtoConfig {

	@Bean
	public ConfigurationPopulator configurationPopulator() {
		return new ConfigurationPopulator();
	}

	@Bean
	public DtoConfigurationConverter dtoConfigurationConverter() {
		DtoConfigurationConverter converter = new DtoConfigurationConverter();
		converter.addPopulator(configurationPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoConfigurationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoConfigurationConverter());
		mapping.setTypeCode(ConfigurationDto.class.getSimpleName());

		return mapping;
	}
}
