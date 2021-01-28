package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoEnumerationConverter;
import com.beanframework.core.converter.populator.EnumerationPopulator;
import com.beanframework.core.data.EnumerationDto;

@Configuration
public class EnumerationDtoConfig {

	@Bean
	public EnumerationPopulator enumerationPopulator() {
		return new EnumerationPopulator();
	}

	@Bean
	public DtoEnumerationConverter dtoEnumerationConverter() {
		DtoEnumerationConverter converter = new DtoEnumerationConverter();
		converter.addPopulator(enumerationPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoEnumerationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoEnumerationConverter());
		mapping.setTypeCode(EnumerationDto.class.getSimpleName());

		return mapping;
	}
}
