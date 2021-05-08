package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CompanyDtoConverter;
import com.beanframework.core.converter.populator.CompanyPopulator;
import com.beanframework.core.data.CompanyDto;

@Configuration
public class CompanyDtoConfig {

	@Bean
	public CompanyPopulator companyPopulator() {
		return new CompanyPopulator();
	}

	@Bean
	public CompanyDtoConverter dtoCompanyConverter() {
		CompanyDtoConverter converter = new CompanyDtoConverter();
		converter.addPopulator(companyPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoCompanyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCompanyConverter());
		mapping.setTypeCode(CompanyDto.class.getSimpleName());

		return mapping;
	}
}
