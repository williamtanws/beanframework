package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Comment;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.CommentEntityConverter;

@Configuration
public class CommentEntityConfig {

	@Autowired
	public CommentEntityConverter commentEntityConverter;

	@Bean
	public ConverterMapping commentEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(commentEntityConverter);
		mapping.setTypeCode(Comment.class.getSimpleName());

		return mapping;
	}
}
