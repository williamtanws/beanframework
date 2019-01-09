package com.beanframework.user.interceptor.userauthority;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityLoadInterceptor implements LoadInterceptor<UserAuthority> {

	@Override
	public void onLoad(UserAuthority model) throws InterceptorException {
		Hibernate.initialize(model.getUserPermission());
		Hibernate.initialize(model.getUserRight());
	}

}
