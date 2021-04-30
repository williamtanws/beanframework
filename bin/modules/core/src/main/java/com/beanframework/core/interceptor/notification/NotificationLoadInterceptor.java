package com.beanframework.core.interceptor.notification;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.notification.domain.Notification;

public class NotificationLoadInterceptor extends AbstractLoadInterceptor<Notification> {

	@Override
	public void onLoad(Notification model, InterceptorContext context) throws InterceptorException {
	}

}
