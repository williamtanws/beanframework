package com.beanframework.customer.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerPrepareInterceptor implements PrepareInterceptor<Customer> {

	@Override
	public void onPrepare(Customer model) throws InterceptorException {
		
	}

}
