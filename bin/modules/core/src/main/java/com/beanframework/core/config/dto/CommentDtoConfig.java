package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CommentDtoConverter;
import com.beanframework.core.converter.populator.CommentPopulator;
import com.beanframework.core.data.CommentDto;

@Configuration
public class CommentDtoConfig {

	@Autowired
	public CommentPopulator commentPopulator;

	@Bean
	public CommentDtoConverter commentDtoConverter() {
		CommentDtoConverter converter = new CommentDtoConverter();
		converter.addPopulator(commentPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping commentDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(commentDtoConverter());
		mapping.setTypeCode(CommentDto.class.getSimpleName());

		return mapping;
	}
}
