package com.beanframework.comment.interceptor;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;

public class CommentPrepareInterceptor implements PrepareInterceptor<Comment> {

	@Override
	public void onPrepare(Comment model) throws InterceptorException {
		if(model.getVisibled() == null)
			model.setVisibled(Boolean.TRUE);
	}

}