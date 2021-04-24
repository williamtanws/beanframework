package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoImexConverter;
import com.beanframework.core.converter.populator.ImexPopulator;
import com.beanframework.core.data.ImexDto;

@Configuration
public class ImexDtoConfig {

	@Bean
	public ImexPopulator imexPopulator() {
		return new ImexPopulator();
	}

	@Bean
	public DtoImexConverter dtoImexConverter() {
		DtoImexConverter converter = new DtoImexConverter();
		converter.addPopulator(imexPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoImexConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoImexConverter());
		mapping.setTypeCode(ImexDto.class.getSimpleName());

		return mapping;
	}
}
