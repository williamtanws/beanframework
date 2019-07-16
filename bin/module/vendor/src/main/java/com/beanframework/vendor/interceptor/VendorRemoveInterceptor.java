package com.beanframework.vendor.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.vendor.domain.Vendor;

public class VendorRemoveInterceptor extends AbstractRemoveInterceptor<Vendor> {

	@Override
	public void onRemove(Vendor model, InterceptorContext context) throws InterceptorException {
	}

}
