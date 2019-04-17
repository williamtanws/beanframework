package com.beanframework.customer.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerListLoadInterceptor extends AbstractLoadInterceptor<Customer> {

	@Override
	public void onLoad(Customer model, InterceptorContext context) throws InterceptorException {
		Customer prototype = new Customer();
		loadCommonProperties(model, prototype, context);
		prototype.setType(model.getType());
		prototype.setPassword(model.getPassword());
		prototype.setAccountNonExpired(model.getAccountNonExpired());
		prototype.setAccountNonLocked(model.getAccountNonLocked());
		prototype.setCredentialsNonExpired(model.getCredentialsNonExpired());
		prototype.setEnabled(model.getEnabled());
		prototype.setName(model.getName());

		model = prototype;
	}

}
