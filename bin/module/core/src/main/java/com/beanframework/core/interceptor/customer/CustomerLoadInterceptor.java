package com.beanframework.core.interceptor.customer;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.Customer;

public class CustomerLoadInterceptor extends AbstractLoadInterceptor<Customer> {

	@Override
	public void onLoad(Customer model, InterceptorContext context) throws InterceptorException {
	}
}
