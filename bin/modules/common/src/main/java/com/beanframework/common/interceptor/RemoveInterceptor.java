package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public interface RemoveInterceptor<MODEL> extends Interceptor {
  void onRemove(MODEL model, InterceptorContext context) throws InterceptorException;
}
