package com.beanframework.core.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.logentry.domain.Logentry;

public class LogentryValidateInterceptor extends AbstractValidateInterceptor<Logentry> {

  @Override
  public void onValidate(Logentry model, InterceptorContext context) throws InterceptorException {
    super.onValidate(model, context);

  }
}
