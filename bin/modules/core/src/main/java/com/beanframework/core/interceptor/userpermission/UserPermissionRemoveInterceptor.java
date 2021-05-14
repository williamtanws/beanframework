package com.beanframework.core.interceptor.userpermission;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionRemoveInterceptor extends AbstractRemoveInterceptor<UserPermission> {

  @Override
  public void onRemove(UserPermission model, InterceptorContext context)
      throws InterceptorException {

  }
}
