package com.beanframework.user.interceptor.user;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.User;

public class UserValidateInterceptor extends AbstractValidateInterceptor<User> {

	@Override
	public void onValidate(User model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
