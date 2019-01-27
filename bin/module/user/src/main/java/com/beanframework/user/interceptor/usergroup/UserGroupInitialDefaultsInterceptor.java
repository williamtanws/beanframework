package com.beanframework.user.interceptor.usergroup;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupInitialDefaultsInterceptor implements InitialDefaultsInterceptor<UserGroup> {

	@Override
	public void onInitialDefaults(UserGroup model) throws InterceptorException {
	}

}
