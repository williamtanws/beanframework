package com.beanframework.core.interceptor.customer;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerValidateInterceptor extends AbstractValidateInterceptor<Customer> {

	@Override
	public void onValidate(Customer model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
