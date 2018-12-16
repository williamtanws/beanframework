package com.beanframework.user.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.user.domain.User;

public class UserAuthorityValidateInterceptor implements ValidateInterceptor<User> {

	@Override
	public void onValidate(User model) throws InterceptorException {
		
		
	}

}
