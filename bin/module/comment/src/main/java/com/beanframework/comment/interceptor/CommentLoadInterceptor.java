package com.beanframework.comment.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;

public class CommentLoadInterceptor extends AbstractLoadInterceptor<Comment> {

	@Override
	public void onLoad(Comment model, InterceptorContext context) throws InterceptorException {
		
		Hibernate.initialize(model.getUser());
		
		Comment prototype = new Comment();
		loadCommonProperties(model, prototype, context);
		prototype.setHtml(model.getHtml());
		prototype.setVisibled(model.getVisibled());
		prototype.setUser(model.getUser());

		model = prototype;
	}

}
