package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.CompanyEntityConverter;
import com.beanframework.user.domain.Company;

@Configuration
public class CompanyEntityConfig {

	@Autowired
	public CompanyEntityConverter companyEntityConverter;

	@Bean
	public ConverterMapping companyEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(companyEntityConverter);
		mapping.setTypeCode(Company.class.getSimpleName());

		return mapping;
	}

}
