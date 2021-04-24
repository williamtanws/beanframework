package com.beanframework.core.interceptor.userright;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightLoadInterceptor extends AbstractLoadInterceptor<UserRight> {

	@Override
	public void onLoad(UserRight model, InterceptorContext context) throws InterceptorException {
	}
}
