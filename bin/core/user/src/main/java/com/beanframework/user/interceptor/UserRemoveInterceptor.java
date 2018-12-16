package com.beanframework.user.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.user.domain.User;

public class UserRemoveInterceptor implements RemoveInterceptor<User> {

	@Override
	public void onRemove(User model) throws InterceptorException {
	}

}
