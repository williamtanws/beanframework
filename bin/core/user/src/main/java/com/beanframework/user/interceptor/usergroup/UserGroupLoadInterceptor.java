package com.beanframework.user.interceptor.usergroup;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupLoadInterceptor implements LoadInterceptor<UserGroup> {

	@Override
	public void onLoad(UserGroup model) throws InterceptorException {

		Hibernate.initialize(model.getUserGroups());
		Hibernate.initialize(model.getUserAuthorities());
	}
}
