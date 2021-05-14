package com.beanframework.core.interceptor.dynamicfieldslot;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotValidateInterceptor
    extends AbstractValidateInterceptor<DynamicFieldSlot> {

  @Override
  public void onValidate(DynamicFieldSlot model, InterceptorContext context)
      throws InterceptorException {
    super.onValidate(model, context);

  }
}
