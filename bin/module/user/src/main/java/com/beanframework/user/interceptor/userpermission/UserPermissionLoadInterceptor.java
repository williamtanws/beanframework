package com.beanframework.user.interceptor.userpermission;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class UserPermissionLoadInterceptor extends AbstractLoadInterceptor<UserPermission> {

	@Override
	public void onLoad(UserPermission model, InterceptorContext context) throws InterceptorException {
		super.onLoad(model, context);

		if (context.getFetchProperties().contains(UserPermission.FIELDS)) {
			Hibernate.initialize(model.getFields());
			for (UserPermissionField field : model.getFields()) {
				Hibernate.initialize(field.getDynamicField());
				Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
		}
	}
}
