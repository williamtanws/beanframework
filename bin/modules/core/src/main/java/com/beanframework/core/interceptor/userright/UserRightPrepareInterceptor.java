package com.beanframework.core.interceptor.userright;

import org.apache.commons.lang3.StringUtils;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightPrepareInterceptor extends AbstractPrepareInterceptor<UserRight> {

  @Override
  public void onPrepare(UserRight model, InterceptorContext context) throws InterceptorException {
    super.onPrepare(model, context);
    for (int i = 0; i < model.getAttributes().size(); i++) {
      if (StringUtils.isBlank(model.getAttributes().get(i).getValue())) {
        model.getAttributes().get(i).setValue(null);
      }
    }
  }
}
