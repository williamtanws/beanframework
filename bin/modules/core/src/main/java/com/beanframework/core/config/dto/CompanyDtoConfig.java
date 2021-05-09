package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CompanyDtoConverter;
import com.beanframework.core.converter.populator.CompanyPopulator;
import com.beanframework.core.data.CompanyDto;

@Configuration
public class CompanyDtoConfig {

	@Autowired
	public CompanyPopulator companyPopulator;

	@Bean
	public CompanyDtoConverter companyDtoConverter() {
		CompanyDtoConverter converter = new CompanyDtoConverter();
		converter.addPopulator(companyPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping companyDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(companyDtoConverter());
		mapping.setTypeCode(CompanyDto.class.getSimpleName());

		return mapping;
	}
}
