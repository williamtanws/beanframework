package com.beanframework.core.interceptor.enumuration;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationLoadInterceptor extends AbstractLoadInterceptor<Enumeration> {

	@Override
	public void onLoad(Enumeration model, InterceptorContext context) throws InterceptorException {
	}

}
