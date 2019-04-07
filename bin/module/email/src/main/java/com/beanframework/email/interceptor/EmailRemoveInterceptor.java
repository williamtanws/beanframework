package com.beanframework.email.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.email.domain.Email;

public class EmailRemoveInterceptor extends AbstractRemoveInterceptor<Email> {

	@Override
	public void onRemove(Email model, InterceptorContext context) throws InterceptorException {
		super.onRemove(model, context);
	}

}
