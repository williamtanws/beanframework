package com.beanframework.user.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityLoadInterceptor implements LoadInterceptor<UserAuthority> {

	@Override
	public void onLoad(UserAuthority model, InterceptorContext context) throws InterceptorException {

	}

}
