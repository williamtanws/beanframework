package com.beanframework.admin.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;

public class AdminLoadInterceptor implements LoadInterceptor<Admin> {

	@Override
	public void onLoad(Admin model) throws InterceptorException {
		Hibernate.initialize(model.getUserGroups());
	}

}
