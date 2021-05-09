package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.EnumerationDtoConverter;
import com.beanframework.core.converter.populator.EnumerationPopulator;
import com.beanframework.core.data.EnumerationDto;

@Configuration
public class EnumerationDtoConfig {

	@Autowired
	public EnumerationPopulator enumerationPopulator;

	@Bean
	public EnumerationDtoConverter enumerationDtoConverter() {
		EnumerationDtoConverter converter = new EnumerationDtoConverter();
		converter.addPopulator(enumerationPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping enumerationDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(enumerationDtoConverter());
		mapping.setTypeCode(EnumerationDto.class.getSimpleName());

		return mapping;
	}
}
