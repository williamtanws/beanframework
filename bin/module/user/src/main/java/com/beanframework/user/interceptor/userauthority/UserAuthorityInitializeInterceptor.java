package com.beanframework.user.interceptor.userauthority;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

public class UserAuthorityInitializeInterceptor implements InitializeInterceptor<UserAuthority> {

	@Override
	public void onInitialize(UserAuthority model, InterceptorContext context) throws InterceptorException {
		Hibernate.initialize(model.getUserPermission());
		for (UserPermissionField field : model.getUserPermission().getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnumerations());
		}

		Hibernate.initialize(model.getUserRight());
		for (UserRightField field : model.getUserRight().getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnumerations());
		}

	}

}
