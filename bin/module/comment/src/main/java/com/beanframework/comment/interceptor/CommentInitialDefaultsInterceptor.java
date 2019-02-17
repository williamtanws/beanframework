package com.beanframework.comment.interceptor;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;

public class CommentInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Comment> {

	@Override
	public void onInitialDefaults(Comment model, InterceptorContext context) throws InterceptorException {
		model.setVisibled(Boolean.TRUE);
	}

}
