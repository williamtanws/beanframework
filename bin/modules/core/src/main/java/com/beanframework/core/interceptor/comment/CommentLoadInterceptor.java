package com.beanframework.core.interceptor.comment;

import com.beanframework.cms.domain.Comment;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;

public class CommentLoadInterceptor extends AbstractLoadInterceptor<Comment> {

	@Override
	public void onLoad(Comment model, InterceptorContext context) throws InterceptorException {
	}

}
