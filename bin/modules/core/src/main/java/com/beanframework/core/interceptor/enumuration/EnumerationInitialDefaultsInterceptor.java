package com.beanframework.core.interceptor.enumuration;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Enumeration> {

	@Override
	public void onInitialDefaults(Enumeration model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
