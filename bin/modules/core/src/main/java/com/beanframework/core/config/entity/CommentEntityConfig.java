package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Comment;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.CommentEntityConverter;

@Configuration
public class CommentEntityConfig {

	@Bean
	public CommentEntityConverter entityCommentConverter() {
		return new CommentEntityConverter();
	}

	@Bean
	public ConverterMapping entityCommentConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCommentConverter());
		mapping.setTypeCode(Comment.class.getSimpleName());

		return mapping;
	}
}
