package com.beanframework.backoffice.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoMediaConverter;
import com.beanframework.core.data.MediaDto;

@Configuration
public class MediaDtoConfig {

	@Bean
	public DtoMediaConverter dtoMediaConverter() {
		return new DtoMediaConverter();
	}

	@Bean
	public ConverterMapping DtoMediaConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMediaConverter());
		mapping.setTypeCode(MediaDto.class.getSimpleName());

		return mapping;
	}
}
