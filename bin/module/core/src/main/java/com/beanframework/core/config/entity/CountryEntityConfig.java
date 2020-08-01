package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityCountryConverter;
import com.beanframework.internationalization.domain.Country;

@Configuration
public class CountryEntityConfig {

	@Bean
	public EntityCountryConverter entityCountryConverter() {
		return new EntityCountryConverter();
	}

	@Bean
	public ConverterMapping entityCountryConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCountryConverter());
		mapping.setTypeCode(Country.class.getSimpleName());

		return mapping;
	}

}
