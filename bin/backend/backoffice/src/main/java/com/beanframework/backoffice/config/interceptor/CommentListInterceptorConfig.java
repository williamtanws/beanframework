package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.comment.domain.Comment;
import com.beanframework.comment.interceptor.CommentListLoadInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.service.ModelService;

@Configuration
public class CommentListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public CommentListLoadInterceptor commentListLoadInterceptor() {
		return new CommentListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping commentListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentListLoadInterceptor());
		mapping.setTypeCode(Comment.class.getSimpleName() + ModelService.DEFAULT_LIST_LOAD_INTERCEPTOR_POSTFIX);

		return mapping;
	}
}
