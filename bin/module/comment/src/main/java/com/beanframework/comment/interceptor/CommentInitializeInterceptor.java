package com.beanframework.comment.interceptor;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;

public class CommentInitializeInterceptor implements InitializeInterceptor<Comment> {

	@Override
	public void onInitialize(Comment model) throws InterceptorException {
	}

}