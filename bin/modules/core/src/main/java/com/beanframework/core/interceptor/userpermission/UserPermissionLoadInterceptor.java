package com.beanframework.core.interceptor.userpermission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionService;

public class UserPermissionLoadInterceptor extends AbstractLoadInterceptor<UserPermission> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionLoadInterceptor.class);

  @Autowired
  private UserPermissionService userPermissionService;

  @Override
  public void onLoad(UserPermission model, InterceptorContext context) throws InterceptorException {
    try {
      userPermissionService.generateUserPermissionAttribute(model);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new InterceptorException(e.getMessage(), e);
    }
  }
}
