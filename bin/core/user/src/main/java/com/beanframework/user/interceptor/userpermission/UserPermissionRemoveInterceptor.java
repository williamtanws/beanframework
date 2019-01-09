package com.beanframework.user.interceptor.userpermission;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionRemoveInterceptor implements RemoveInterceptor<UserPermission> {

	@Override
	public void onRemove(UserPermission model) throws InterceptorException {
	}

}
