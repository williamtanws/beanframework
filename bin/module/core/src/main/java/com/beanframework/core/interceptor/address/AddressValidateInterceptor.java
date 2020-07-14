package com.beanframework.core.interceptor.address;

import com.beanframework.address.domain.Address;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;

public class AddressValidateInterceptor extends AbstractValidateInterceptor<Address> {

	@Override
	public void onValidate(Address model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
