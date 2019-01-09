package com.beanframework.user.interceptor.userpermission;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionLoadInterceptor implements LoadInterceptor<UserPermission> {

	@Override
	public void onLoad(UserPermission model) throws InterceptorException {
	}
}
