package com.beanframework.vendor.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.vendor.domain.Vendor;

public class VendorLoadInterceptor extends AbstractLoadInterceptor<Vendor> {

	@Override
	public void onLoad(Vendor model, InterceptorContext context) throws InterceptorException {
	}
}
