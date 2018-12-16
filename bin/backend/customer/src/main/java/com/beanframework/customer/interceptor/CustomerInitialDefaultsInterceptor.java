package com.beanframework.customer.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Customer> {

	@Override
	public void onInitialDefaults(Customer model) throws InterceptorException {
		model.setEnabled(true);
		model.setAccountNonExpired(true);
		model.setAccountNonLocked(true);
		model.setCredentialsNonExpired(true);
	}

}
