package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CommentDtoConverter;
import com.beanframework.core.converter.populator.CommentPopulator;
import com.beanframework.core.data.CommentDto;

@Configuration
public class CommentDtoConfig {

	@Bean
	public CommentPopulator commentPopulator() {
		return new CommentPopulator();
	}

	@Bean
	public CommentDtoConverter dtoCommentConverter() {
		CommentDtoConverter converter = new CommentDtoConverter();
		converter.addPopulator(commentPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoCommentConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCommentConverter());
		mapping.setTypeCode(CommentDto.class.getSimpleName());

		return mapping;
	}
}
