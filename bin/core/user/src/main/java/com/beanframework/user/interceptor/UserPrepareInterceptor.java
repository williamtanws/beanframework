package com.beanframework.user.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.user.domain.User;

public class UserPrepareInterceptor implements PrepareInterceptor<User> {

	@Override
	public void onPrepare(User model) throws InterceptorException {
	}

}
