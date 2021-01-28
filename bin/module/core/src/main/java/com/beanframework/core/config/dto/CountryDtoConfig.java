package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoCountryConverter;
import com.beanframework.core.converter.populator.CountryPopulator;
import com.beanframework.core.data.CountryDto;

@Configuration
public class CountryDtoConfig {

	@Bean
	public CountryPopulator countryPopulator() {
		return new CountryPopulator();
	}

	@Bean
	public DtoCountryConverter dtoCountryConverter() {
		DtoCountryConverter converter = new DtoCountryConverter();
		converter.addPopulator(countryPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoCountryConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCountryConverter());
		mapping.setTypeCode(CountryDto.class.getSimpleName());

		return mapping;
	}
}
