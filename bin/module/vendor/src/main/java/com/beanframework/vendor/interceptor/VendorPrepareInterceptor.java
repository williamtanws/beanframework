package com.beanframework.vendor.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.vendor.domain.Vendor;

public class VendorPrepareInterceptor implements PrepareInterceptor<Vendor> {

	@Override
	public void onPrepare(Vendor model, InterceptorContext context) throws InterceptorException {

		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
}
