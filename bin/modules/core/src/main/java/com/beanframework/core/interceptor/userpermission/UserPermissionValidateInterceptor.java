package com.beanframework.core.interceptor.userpermission;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionValidateInterceptor extends AbstractValidateInterceptor<UserPermission> {

  @Override
  public void onValidate(UserPermission model, InterceptorContext context)
      throws InterceptorException {
    super.onValidate(model, context);

  }

}
