package com.beanframework.vendor.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.vendor.domain.Vendor;

public class VendorValidateInterceptor implements ValidateInterceptor<Vendor> {

	@Override
	public void onValidate(Vendor model, InterceptorContext context) throws InterceptorException {

	}

}
