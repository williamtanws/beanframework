package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityAdminConverter;
import com.beanframework.user.domain.Admin;

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
