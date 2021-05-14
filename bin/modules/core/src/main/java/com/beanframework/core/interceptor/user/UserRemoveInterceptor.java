package com.beanframework.core.interceptor.user;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.User;

public class UserRemoveInterceptor extends AbstractRemoveInterceptor<User> {

  @Override
  public void onRemove(User model, InterceptorContext context) throws InterceptorException {}

}
