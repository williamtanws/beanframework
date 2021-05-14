package com.beanframework.core.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.logentry.domain.Logentry;

public class LogentryInitialDefaultsInterceptor
    extends AbstractInitialDefaultsInterceptor<Logentry> {

  @Override
  public void onInitialDefaults(Logentry model, InterceptorContext context)
      throws InterceptorException {
    super.onInitialDefaults(model, context);
  }

}
