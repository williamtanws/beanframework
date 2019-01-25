package com.beanframework.comment.interceptor;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;

public class CommentRemoveInterceptor implements RemoveInterceptor<Comment> {

	@Override
	public void onRemove(Comment model) throws InterceptorException {
	}

}
