package com.beanframework.enumuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationInitializeInterceptor implements InitializeInterceptor<Enumeration> {

	@Override
	public void onInitialize(Enumeration model, InterceptorContext context) throws InterceptorException {
	}

}
