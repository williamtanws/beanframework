package com.beanframework.admin.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.user.domain.UserField;

public class AdminInitializeInterceptor implements InitializeInterceptor<Admin> {

	@Override
	public void onInitialize(Admin model, InterceptorContext context) throws InterceptorException {
		Hibernate.initialize(model.getUserGroups());

		Hibernate.initialize(model.getFields());
		for (UserField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnumerations());
		}

	}
}
