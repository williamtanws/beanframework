package com.beanframework.email.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.email.domain.Email;

public class EmailRemoveInterceptor implements RemoveInterceptor<Email> {

	@Override
	public void onRemove(Email model, InterceptorContext context) throws InterceptorException {
	}

}
