package com.beanframework.comment.interceptor;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;

public class CommentLoadInterceptor implements LoadInterceptor<Comment> {

	@Override
	public void onLoad(Comment model) throws InterceptorException {
	}

}
