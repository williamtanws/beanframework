package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CurrencyDtoConverter;
import com.beanframework.core.converter.populator.CurrencyPopulator;
import com.beanframework.core.data.CurrencyDto;

@Configuration
public class CurrencyDtoConfig {

	@Autowired
	public CurrencyPopulator currencyPopulator;

	@Bean
	public CurrencyDtoConverter currencyDtoConverter() {
		CurrencyDtoConverter converter = new CurrencyDtoConverter();
		converter.addPopulator(currencyPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping currencyDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(currencyDtoConverter());
		mapping.setTypeCode(CurrencyDto.class.getSimpleName());

		return mapping;
	}
}
