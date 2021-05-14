package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.InterceptorException;

public abstract class AbstractLoadInterceptor<T extends GenericEntity>
    implements LoadInterceptor<T> {

  @Override
  public void onLoad(T model, InterceptorContext context) throws InterceptorException {}

  public void generateDynamicFields() {

  }
}
