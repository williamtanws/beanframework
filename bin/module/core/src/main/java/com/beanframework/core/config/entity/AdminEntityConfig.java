package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntityAdminConverter;

@Configuration
public class AdminEntityConfig {

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
