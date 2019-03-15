package com.beanframework.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoAdminConverter;
import com.beanframework.core.converter.DtoConfigurationConverter;
import com.beanframework.core.data.AdminDto;
import com.beanframework.core.data.ConfigurationDto;

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
