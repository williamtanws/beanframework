package com.beanframework.user.interceptor.usergroup;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupLoadInterceptor extends AbstractLoadInterceptor<UserGroup> {

	@Override
	public void onLoad(UserGroup model, InterceptorContext context) throws InterceptorException {
	}
}
