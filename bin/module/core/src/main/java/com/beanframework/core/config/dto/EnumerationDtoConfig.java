package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoEnumerationConverter;
import com.beanframework.core.data.EnumerationDto;

@Configuration
public class EnumerationDtoConfig {

	@Bean
	public DtoEnumerationConverter dtoEnumerationConverter() {
		return new DtoEnumerationConverter();
	}

	@Bean
	public ConverterMapping dtoEnumerationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoEnumerationConverter());
		mapping.setTypeCode(EnumerationDto.class.getSimpleName());

		return mapping;
	}
}
