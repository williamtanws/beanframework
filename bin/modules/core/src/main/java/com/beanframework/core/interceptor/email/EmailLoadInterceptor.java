package com.beanframework.core.interceptor.email;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.email.domain.Email;

public class EmailLoadInterceptor extends AbstractLoadInterceptor<Email> {

  @Override
  public void onLoad(Email model, InterceptorContext context) throws InterceptorException {}

}
