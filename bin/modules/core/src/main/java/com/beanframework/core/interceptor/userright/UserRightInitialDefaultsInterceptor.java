package com.beanframework.core.interceptor.userright;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightService;

public class UserRightInitialDefaultsInterceptor
    extends AbstractInitialDefaultsInterceptor<UserRight> {

  protected static Logger LOGGER =
      LoggerFactory.getLogger(UserRightInitialDefaultsInterceptor.class);

  @Autowired
  private UserRightService userRightService;

  @Override
  public void onInitialDefaults(UserRight model, InterceptorContext context)
      throws InterceptorException {
    try {
      userRightService.generateUserRightAttribute(model);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new InterceptorException(e.getMessage(), e);
    }
  }

}
