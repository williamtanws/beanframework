package com.beanframework.customer.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerPrepareInterceptor implements PrepareInterceptor<Customer> {

	@Override
	public void onPrepare(Customer model, InterceptorContext context) throws InterceptorException {

		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
}
