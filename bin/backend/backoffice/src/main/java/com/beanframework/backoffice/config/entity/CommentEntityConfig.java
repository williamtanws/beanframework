package com.beanframework.backoffice.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntityCommentConverter;

@Configuration
public class CommentEntityConfig {

	@Bean
	public EntityCommentConverter entityCommentConverter() {
		return new EntityCommentConverter();
	}

	@Bean
	public ConverterMapping entityCommentConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCommentConverter());
		mapping.setTypeCode(Comment.class.getSimpleName());

		return mapping;
	}
}
