package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoAddressConverter;
import com.beanframework.core.converter.populator.AddressPopulator;
import com.beanframework.core.data.AddressDto;

@Configuration
public class AddressDtoConfig {

	@Bean
	public AddressPopulator addressPopulator() {
		return new AddressPopulator();
	}

	@Bean
	public DtoAddressConverter dtoAddressConverter() {
		DtoAddressConverter converter = new DtoAddressConverter();
		converter.addPopulator(addressPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoAddressConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoAddressConverter());
		mapping.setTypeCode(AddressDto.class.getSimpleName());

		return mapping;
	}
}
