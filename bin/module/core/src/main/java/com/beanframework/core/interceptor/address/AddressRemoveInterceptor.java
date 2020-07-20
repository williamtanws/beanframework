package com.beanframework.core.interceptor.address;

import com.beanframework.address.domain.Address;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;

public class AddressRemoveInterceptor extends AbstractRemoveInterceptor<Address> {

	@Override
	public void onRemove(Address model, InterceptorContext context) throws InterceptorException {
	}

}