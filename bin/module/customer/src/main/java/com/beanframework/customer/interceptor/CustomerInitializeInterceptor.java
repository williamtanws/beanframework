package com.beanframework.customer.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserField;

public class CustomerInitializeInterceptor implements InitializeInterceptor<Customer> {

	@Override
	public void onInitialize(Customer model) throws InterceptorException {
		Hibernate.initialize(model.getUserGroups());

		Hibernate.initialize(model.getFields());
		for (UserField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnumerations());
		}
	}
}
