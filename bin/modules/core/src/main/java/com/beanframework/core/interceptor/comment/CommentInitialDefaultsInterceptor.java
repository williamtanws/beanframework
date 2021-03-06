package com.beanframework.core.interceptor.comment;

import com.beanframework.cms.domain.Comment;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;

public class CommentInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Comment> {

  @Override
  public void onInitialDefaults(Comment model, InterceptorContext context)
      throws InterceptorException {
    super.onInitialDefaults(model, context);
    model.setVisibled(Boolean.TRUE);
  }

}
