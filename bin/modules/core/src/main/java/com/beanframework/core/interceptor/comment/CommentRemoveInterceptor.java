package com.beanframework.core.interceptor.comment;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;

public class CommentRemoveInterceptor extends AbstractRemoveInterceptor<Comment> {

	@Override
	public void onRemove(Comment model, InterceptorContext context) throws InterceptorException {
	}

}
