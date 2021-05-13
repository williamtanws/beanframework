package com.beanframework.core.interceptor.address;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.Address;

public class AddressValidateInterceptor extends AbstractValidateInterceptor<Address> {

  @Override
  public void onValidate(Address model, InterceptorContext context) throws InterceptorException {
    super.onValidate(model, context);

  }
}
