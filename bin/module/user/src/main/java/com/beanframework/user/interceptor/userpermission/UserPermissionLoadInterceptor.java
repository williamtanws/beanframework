package com.beanframework.user.interceptor.userpermission;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class UserPermissionLoadInterceptor implements LoadInterceptor<UserPermission> {

	@Override
	public void onLoad(UserPermission model, InterceptorContext context) throws InterceptorException {

		for (UserPermissionField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnumerations());
		}
	}
}
