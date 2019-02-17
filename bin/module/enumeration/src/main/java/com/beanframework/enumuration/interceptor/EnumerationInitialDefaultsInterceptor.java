package com.beanframework.enumuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Enumeration> {

	@Override
	public void onInitialDefaults(Enumeration model, InterceptorContext context) throws InterceptorException {
	}

}
