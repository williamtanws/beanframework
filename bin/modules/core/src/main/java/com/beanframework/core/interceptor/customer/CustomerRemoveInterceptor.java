package com.beanframework.core.interceptor.customer;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.Customer;

public class CustomerRemoveInterceptor extends AbstractRemoveInterceptor<Customer> {

	@Override
	public void onRemove(Customer model, InterceptorContext context) throws InterceptorException {
	}

}
