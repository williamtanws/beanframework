package com.beanframework.customer.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerLoadInterceptor implements LoadInterceptor<Customer> {

	@Override
	public void onLoad(Customer model, InterceptorContext context) throws InterceptorException {

	}

}
