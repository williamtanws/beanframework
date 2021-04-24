package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoCurrencyConverter;
import com.beanframework.core.converter.populator.CurrencyPopulator;
import com.beanframework.core.data.CurrencyDto;

@Configuration
public class CurrencyDtoConfig {

	@Bean
	public CurrencyPopulator currencyPopulator() {
		return new CurrencyPopulator();
	}

	@Bean
	public DtoCurrencyConverter dtoCurrencyConverter() {
		DtoCurrencyConverter converter = new DtoCurrencyConverter();
		converter.addPopulator(currencyPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoCurrencyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCurrencyConverter());
		mapping.setTypeCode(CurrencyDto.class.getSimpleName());

		return mapping;
	}
}
