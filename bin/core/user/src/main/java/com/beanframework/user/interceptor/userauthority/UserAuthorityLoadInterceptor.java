package com.beanframework.user.interceptor.userauthority;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

public class UserAuthorityLoadInterceptor implements LoadInterceptor<UserAuthority> {

	@Override
	public void onLoad(UserAuthority model) throws InterceptorException {
		Hibernate.initialize(model.getUserPermission());
		for (UserPermissionField field : model.getUserPermission().getFields()) {
			Hibernate.initialize(field.getDynamicField().getValues());
		}
		
		Hibernate.initialize(model.getUserRight());
		for (UserRightField field : model.getUserRight().getFields()) {
			Hibernate.initialize(field.getDynamicField().getValues());
		}
	}

}
