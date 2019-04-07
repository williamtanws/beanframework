package com.beanframework.comment.interceptor;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;

public class CommentPrepareInterceptor extends AbstractPrepareInterceptor<Comment> {

	@Override
	public void onPrepare(Comment model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
		if (model.getVisibled() == null)
			model.setVisibled(Boolean.TRUE);
	}

}
