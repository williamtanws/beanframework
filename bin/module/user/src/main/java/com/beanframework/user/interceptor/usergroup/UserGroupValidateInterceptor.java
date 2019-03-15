package com.beanframework.user.interceptor.usergroup;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupValidateInterceptor implements ValidateInterceptor<UserGroup> {

	@Override
	public void onValidate(UserGroup model, InterceptorContext context) throws InterceptorException {

	}

}
