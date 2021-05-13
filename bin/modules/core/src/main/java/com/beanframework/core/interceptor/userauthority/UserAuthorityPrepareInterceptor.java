package com.beanframework.core.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityPrepareInterceptor extends AbstractPrepareInterceptor<UserAuthority> {

  @Override
  public void onPrepare(UserAuthority model, InterceptorContext context)
      throws InterceptorException {
    super.onPrepare(model, context);

  }

}
