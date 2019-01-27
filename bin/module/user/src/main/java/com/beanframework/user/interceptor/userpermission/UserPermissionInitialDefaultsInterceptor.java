package com.beanframework.user.interceptor.userpermission;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionInitialDefaultsInterceptor implements InitialDefaultsInterceptor<UserPermission> {

	@Override
	public void onInitialDefaults(UserPermission model) throws InterceptorException {
	}

}
