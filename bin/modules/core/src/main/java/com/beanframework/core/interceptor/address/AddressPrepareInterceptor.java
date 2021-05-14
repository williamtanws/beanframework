package com.beanframework.core.interceptor.address;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.domain.Address;

public class AddressPrepareInterceptor extends AbstractPrepareInterceptor<Address> {

  @Override
  public void onPrepare(Address model, InterceptorContext context) throws InterceptorException {
    super.onPrepare(model, context);
  }

}
