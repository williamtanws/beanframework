package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoCountryConverter;
import com.beanframework.core.data.CountryDto;

@Configuration
public class CountryDtoConfig {

	@Bean
	public DtoCountryConverter dtoCountryConverter() {
		return new DtoCountryConverter();
	}

	@Bean
	public ConverterMapping dtoCountryConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCountryConverter());
		mapping.setTypeCode(CountryDto.class.getSimpleName());

		return mapping;
	}
}
