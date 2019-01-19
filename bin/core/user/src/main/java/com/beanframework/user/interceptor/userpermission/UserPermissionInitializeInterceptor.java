package com.beanframework.user.interceptor.userpermission;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class UserPermissionInitializeInterceptor implements InitializeInterceptor<UserPermission> {

	@Override
	public void onInitialize(UserPermission model) throws InterceptorException {
		
		for (UserPermissionField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnums());
		}
	}
}
