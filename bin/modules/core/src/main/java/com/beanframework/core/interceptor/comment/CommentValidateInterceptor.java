package com.beanframework.core.interceptor.comment;

import com.beanframework.cms.domain.Comment;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;

public class CommentValidateInterceptor extends AbstractValidateInterceptor<Comment> {

  @Override
  public void onValidate(Comment model, InterceptorContext context) throws InterceptorException {
    super.onValidate(model, context);

  }
}
