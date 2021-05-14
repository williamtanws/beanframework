package com.beanframework.core.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.logentry.domain.Logentry;

public class LogentryLoadInterceptor extends AbstractLoadInterceptor<Logentry> {

  @Override
  public void onLoad(Logentry model, InterceptorContext context) throws InterceptorException {

  }

}
