package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.MediaDtoConverter;
import com.beanframework.core.converter.populator.MediaPopulator;
import com.beanframework.core.data.MediaDto;

@Configuration
public class MediaDtoConfig {

	@Bean
	public MediaPopulator mediaPopulator() {
		return new MediaPopulator();
	}

	@Bean
	public MediaDtoConverter dtoMediaConverter() {
		MediaDtoConverter converter = new MediaDtoConverter();
		converter.addPopulator(mediaPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoMediaConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMediaConverter());
		mapping.setTypeCode(MediaDto.class.getSimpleName());

		return mapping;
	}
}
