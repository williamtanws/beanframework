package com.beanframework.customer.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerValidateInterceptor implements ValidateInterceptor<Customer> {

	@Override
	public void onValidate(Customer model, InterceptorContext context) throws InterceptorException {

	}

}
