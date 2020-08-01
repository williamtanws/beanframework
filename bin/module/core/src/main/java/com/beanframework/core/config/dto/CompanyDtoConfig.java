package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoCompanyConverter;
import com.beanframework.core.data.CompanyDto;

@Configuration
public class CompanyDtoConfig {

	@Bean
	public DtoCompanyConverter dtoCompanyConverter() {
		return new DtoCompanyConverter();
	}

	@Bean
	public ConverterMapping dtoCompanyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCompanyConverter());
		mapping.setTypeCode(CompanyDto.class.getSimpleName());

		return mapping;
	}
}
