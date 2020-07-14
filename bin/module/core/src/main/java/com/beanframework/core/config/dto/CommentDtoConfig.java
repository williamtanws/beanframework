package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoCommentConverter;
import com.beanframework.core.data.CommentDto;

@Configuration
public class CommentDtoConfig {

	@Bean
	public DtoCommentConverter dtoCommentConverter() {
		return new DtoCommentConverter();
	}

	@Bean
	public ConverterMapping dtoCommentConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCommentConverter());
		mapping.setTypeCode(CommentDto.class.getSimpleName());

		return mapping;
	}
}
