package com.beanframework.core.interceptor.address;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.Address;

public class AddressLoadInterceptor extends AbstractLoadInterceptor<Address> {

	@Override
	public void onLoad(Address model, InterceptorContext context) throws InterceptorException {
	}

}
