package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityCurrencyConverter;
import com.beanframework.internationalization.domain.Currency;

@Configuration
public class CurrencyEntityConfig {

	@Bean
	public EntityCurrencyConverter entityCurrencyConverter() {
		return new EntityCurrencyConverter();
	}

	@Bean
	public ConverterMapping entityCurrencyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCurrencyConverter());
		mapping.setTypeCode(Currency.class.getSimpleName());

		return mapping;
	}

}
