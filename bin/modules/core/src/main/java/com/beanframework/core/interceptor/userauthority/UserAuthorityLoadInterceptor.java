package com.beanframework.core.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityLoadInterceptor extends AbstractLoadInterceptor<UserAuthority> {

	@Override
	public void onLoad(UserAuthority model, InterceptorContext context) throws InterceptorException {
	}

}
