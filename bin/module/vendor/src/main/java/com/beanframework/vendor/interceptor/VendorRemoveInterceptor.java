package com.beanframework.vendor.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.vendor.domain.Vendor;

public class VendorRemoveInterceptor implements RemoveInterceptor<Vendor> {

	@Override
	public void onRemove(Vendor model, InterceptorContext context) throws InterceptorException {
	}

}
