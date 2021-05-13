package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public abstract class AbstractInitialDefaultsInterceptor<T>
    implements InitialDefaultsInterceptor<T> {

  @Override
  public void onInitialDefaults(T model, InterceptorContext context) throws InterceptorException {

  }
}
