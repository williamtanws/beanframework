package com.beanframework.core.interceptor.company;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.Company;

public class CompanyRemoveInterceptor extends AbstractRemoveInterceptor<Company> {

  @Override
  public void onRemove(Company model, InterceptorContext context) throws InterceptorException {}

}
