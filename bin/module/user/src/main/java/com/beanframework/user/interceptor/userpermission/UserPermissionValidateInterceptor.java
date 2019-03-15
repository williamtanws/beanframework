package com.beanframework.user.interceptor.userpermission;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionValidateInterceptor implements ValidateInterceptor<UserPermission> {

	@Override
	public void onValidate(UserPermission model, InterceptorContext context) throws InterceptorException {

	}

}
