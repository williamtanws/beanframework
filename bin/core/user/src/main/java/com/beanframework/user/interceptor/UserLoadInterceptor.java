package com.beanframework.user.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.User;

public class UserLoadInterceptor implements LoadInterceptor<User> {

	@Override
	public void onLoad(User model) throws InterceptorException {
	}

}
