package com.beanframework.core.interceptor.notification;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.notification.domain.Notification;

public class NotificationRemoveInterceptor extends AbstractRemoveInterceptor<Notification> {

  @Override
  public void onRemove(Notification model, InterceptorContext context)
      throws InterceptorException {}

}
