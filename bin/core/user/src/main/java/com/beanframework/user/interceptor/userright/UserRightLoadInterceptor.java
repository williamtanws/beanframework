package com.beanframework.user.interceptor.userright;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class UserRightLoadInterceptor implements LoadInterceptor<UserRight> {

	@Override
	public void onLoad(UserRight model) throws InterceptorException {
		
		for (UserRightField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnums());
		}
	}
}
