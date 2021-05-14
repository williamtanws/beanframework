package com.beanframework.core.interceptor.cronjob;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobPrepareInterceptor extends AbstractPrepareInterceptor<Cronjob> {

  @Override
  public void onPrepare(Cronjob model, InterceptorContext context) throws InterceptorException {
    super.onPrepare(model, context);

  }

}
