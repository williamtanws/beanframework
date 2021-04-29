package com.beanframework.core.interceptor.notification;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.notification.domain.Notification;

public class NotificationValidateInterceptor extends AbstractValidateInterceptor<Notification> {

	@Override
	public void onValidate(Notification model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
