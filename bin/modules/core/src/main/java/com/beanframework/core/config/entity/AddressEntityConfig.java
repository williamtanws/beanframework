package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.AddressEntityConverter;
import com.beanframework.user.domain.Address;

@Configuration
public class AddressEntityConfig {

	@Bean
	public AddressEntityConverter entityAddressConverter() {
		return new AddressEntityConverter();
	}

	@Bean
	public ConverterMapping entityAddressConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityAddressConverter());
		mapping.setTypeCode(Address.class.getSimpleName());

		return mapping;
	}

}
