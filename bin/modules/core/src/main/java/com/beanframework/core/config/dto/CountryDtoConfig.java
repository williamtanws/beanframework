package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CountryDtoConverter;
import com.beanframework.core.converter.populator.CountryPopulator;
import com.beanframework.core.data.CountryDto;

@Configuration
public class CountryDtoConfig {

	@Bean
	public CountryPopulator countryPopulator() {
		return new CountryPopulator();
	}

	@Bean
	public CountryDtoConverter dtoCountryConverter() {
		CountryDtoConverter converter = new CountryDtoConverter();
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
