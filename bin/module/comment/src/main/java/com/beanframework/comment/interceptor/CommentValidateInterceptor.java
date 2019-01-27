package com.beanframework.comment.interceptor;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;

public class CommentValidateInterceptor implements ValidateInterceptor<Comment> {

	@Override
	public void onValidate(Comment model) throws InterceptorException {

	}
}
