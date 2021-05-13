package com.beanframework.core.interceptor.cronjob;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Cronjob> {

  @Override
  public void onInitialDefaults(Cronjob model, InterceptorContext context)
      throws InterceptorException {
    super.onInitialDefaults(model, context);
  }

}
