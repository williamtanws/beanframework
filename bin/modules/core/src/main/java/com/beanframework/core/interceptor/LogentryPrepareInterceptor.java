package com.beanframework.core.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.logentry.domain.Logentry;

public class LogentryPrepareInterceptor extends AbstractPrepareInterceptor<Logentry> {

  @Override
  public void onPrepare(Logentry model, InterceptorContext context) throws InterceptorException {
    super.onPrepare(model, context);
  }

}
