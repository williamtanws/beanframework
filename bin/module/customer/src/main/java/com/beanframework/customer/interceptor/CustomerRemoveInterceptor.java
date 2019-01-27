package com.beanframework.customer.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerRemoveInterceptor implements RemoveInterceptor<Customer> {

	@Override
	public void onRemove(Customer model) throws InterceptorException {
	}

}
