package com.beanframework.user.interceptor.userright;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightLoadInterceptor implements LoadInterceptor<UserRight> {

	@Override
	public void onLoad(UserRight model) throws InterceptorException {
	}
}
