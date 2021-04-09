package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityCompanyConverter;
import com.beanframework.user.domain.Company;

@Configuration
public class CompanyEntityConfig {

	@Bean
	public EntityCompanyConverter entityCompanyConverter() {
		return new EntityCompanyConverter();
	}

	@Bean
	public ConverterMapping entityCompanyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCompanyConverter());
		mapping.setTypeCode(Company.class.getSimpleName());

		return mapping;
	}

}