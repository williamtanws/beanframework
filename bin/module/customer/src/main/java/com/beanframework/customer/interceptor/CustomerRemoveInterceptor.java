package com.beanframework.customer.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerRemoveInterceptor extends AbstractRemoveInterceptor<Customer> {

	@Override
	public void onRemove(Customer model, InterceptorContext context) throws InterceptorException {
		super.onRemove(model, context);
	}

}
