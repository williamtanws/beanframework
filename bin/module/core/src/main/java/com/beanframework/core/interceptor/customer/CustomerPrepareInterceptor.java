package com.beanframework.core.interceptor.customer;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.customer.domain.Customer;

public class CustomerPrepareInterceptor extends AbstractPrepareInterceptor<Customer> {

	@Override
	public void onPrepare(Customer model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
}
