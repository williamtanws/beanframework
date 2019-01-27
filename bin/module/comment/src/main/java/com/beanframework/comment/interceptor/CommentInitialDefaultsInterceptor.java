package com.beanframework.comment.interceptor;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;

public class CommentInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Comment> {

	@Override
	public void onInitialDefaults(Comment model) throws InterceptorException {
		model.setVisibled(Boolean.TRUE);
	}

}
