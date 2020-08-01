package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoAddressConverter;
import com.beanframework.core.data.AddressDto;

@Configuration
public class AddressDtoConfig {

	@Bean
	public DtoAddressConverter dtoAddressConverter() {
		return new DtoAddressConverter();
	}

	@Bean
	public ConverterMapping dtoAddressConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoAddressConverter());
		mapping.setTypeCode(AddressDto.class.getSimpleName());

		return mapping;
	}
}
