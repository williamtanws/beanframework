package com.beanframework.user.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupValidateInterceptor implements ValidateInterceptor<UserGroup> {

	@Override
	public void onValidate(UserGroup model) throws InterceptorException {

	}

}
