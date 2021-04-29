package com.beanframework.core.interceptor.notification;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.notification.domain.Notification;

public class NotificationInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Notification> {

	@Override
	public void onInitialDefaults(Notification model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
