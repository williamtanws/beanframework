package com.beanframework.user.interceptor.usergroup;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupLoadInterceptor implements LoadInterceptor<UserGroup> {

	@Override
	public void onLoad(UserGroup model, InterceptorContext context) throws InterceptorException {

	}
}
