package com.beanframework.core.interceptor.vendor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.Vendor;

public class VendorValidateInterceptor extends AbstractValidateInterceptor<Vendor> {

  @Override
  public void onValidate(Vendor model, InterceptorContext context) throws InterceptorException {
    super.onValidate(model, context);

  }

}
