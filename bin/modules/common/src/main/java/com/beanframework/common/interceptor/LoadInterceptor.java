package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public interface LoadInterceptor<MODEL> extends Interceptor {
  void onLoad(MODEL model, InterceptorContext context) throws InterceptorException;
}
