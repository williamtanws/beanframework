package com.beanframework.core.interceptor.company;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.domain.Company;

public class CompanyInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Company> {

  @Override
  public void onInitialDefaults(Company model, InterceptorContext context)
      throws InterceptorException {
    super.onInitialDefaults(model, context);
  }

}
