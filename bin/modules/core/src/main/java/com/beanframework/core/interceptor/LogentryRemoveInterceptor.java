package com.beanframework.core.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.logentry.domain.Logentry;

public class LogentryRemoveInterceptor extends AbstractRemoveInterceptor<Logentry> {

  @Override
  public void onRemove(Logentry model, InterceptorContext context) throws InterceptorException {
    try {

    } catch (Exception e) {
      throw new InterceptorException(e.getMessage(), e);
    }
  }

}
