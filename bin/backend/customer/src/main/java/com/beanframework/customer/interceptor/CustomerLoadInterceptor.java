package com.beanframework.customer.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerLoadInterceptor implements LoadInterceptor<Customer> {

	@Override
	public void onLoad(Customer model) throws InterceptorException {
		Hibernate.initialize(model.getUserGroups());
	}

}
