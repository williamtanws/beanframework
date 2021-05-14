package com.beanframework.core.interceptor.userright;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightValidateInterceptor extends AbstractValidateInterceptor<UserRight> {

  @Override
  public void onValidate(UserRight model, InterceptorContext context) throws InterceptorException {
    super.onValidate(model, context);
  }

}
