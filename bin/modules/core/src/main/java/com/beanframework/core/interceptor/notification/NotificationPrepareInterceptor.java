package com.beanframework.core.interceptor.notification;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.notification.domain.Notification;

public class NotificationPrepareInterceptor extends AbstractPrepareInterceptor<Notification> {

  @Override
  public void onPrepare(Notification model, InterceptorContext context)
      throws InterceptorException {
    super.onPrepare(model, context);
  }

}
