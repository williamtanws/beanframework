package com.beanframework.user.interceptor.userpermission;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionLoadInterceptor extends AbstractLoadInterceptor<UserPermission> {

	@Override
	public UserPermission onLoad(UserPermission model, InterceptorContext context) throws InterceptorException {
		UserPermission prototype = new UserPermission();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setSort(model.getSort());

		return prototype;
	}
}
