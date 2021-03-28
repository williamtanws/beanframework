package com.beanframework.core.interceptor.address;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.domain.Address;

public class AddressInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Address> {

	@Override
	public void onInitialDefaults(Address model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
