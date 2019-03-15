package com.beanframework.user.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityPrepareInterceptor implements PrepareInterceptor<UserAuthority> {

	@Override
	public void onPrepare(UserAuthority model, InterceptorContext context) throws InterceptorException {

	}

}
