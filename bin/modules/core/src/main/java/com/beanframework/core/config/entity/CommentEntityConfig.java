package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Comment;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityCommentConverter;

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
