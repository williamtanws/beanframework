package com.beanframework.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.console.converter.DtoAdminConverter;
import com.beanframework.console.converter.DtoConfigurationConverter;
import com.beanframework.console.data.AdminDto;
import com.beanframework.console.data.ConfigurationDto;

@Configuration
public class ConsoleDtoConfig {

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

	@Bean
	public DtoAdminConverter dtoAdminConverter() {
		return new DtoAdminConverter();
	}

	@Bean
	public ConverterMapping dtoAdminConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoAdminConverter());
		mapping.setTypeCode(AdminDto.class.getSimpleName());

		return mapping;
	}
}