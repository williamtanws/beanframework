package com.beanframework.user.interceptor.userright;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class UserRightInitializeInterceptor implements InitializeInterceptor<UserRight> {

	@Override
	public void onInitialize(UserRight model) throws InterceptorException {
		Hibernate.initialize(model.getFields());
		for (UserRightField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnums());
		}
	}
}
