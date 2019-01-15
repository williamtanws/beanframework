package com.beanframework.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.console.converter.EntityAdminConverter;
import com.beanframework.console.converter.EntityConfigurationConverter;

@Configuration
public class ConsoleEntityConfig {

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

	@Bean
	public EntityAdminConverter entityAdminConverter() {
		return new EntityAdminConverter();
	}

	@Bean
	public ConverterMapping entityAdminConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityAdminConverter());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	}

}
