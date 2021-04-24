package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoCommentConverter;
import com.beanframework.core.converter.populator.CommentPopulator;
import com.beanframework.core.data.CommentDto;

@Configuration
public class CommentDtoConfig {

	@Bean
	public CommentPopulator commentPopulator() {
		return new CommentPopulator();
	}

	@Bean
	public DtoCommentConverter dtoCommentConverter() {
		DtoCommentConverter converter = new DtoCommentConverter();
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
