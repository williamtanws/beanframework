package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.CurrencyEntityConverter;
import com.beanframework.internationalization.domain.Currency;

@Configuration
public class CurrencyEntityConfig {

	@Bean
	public CurrencyEntityConverter entityCurrencyConverter() {
		return new CurrencyEntityConverter();
	}

	@Bean
	public ConverterMapping entityCurrencyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCurrencyConverter());
		mapping.setTypeCode(Currency.class.getSimpleName());

		return mapping;
	}

}
