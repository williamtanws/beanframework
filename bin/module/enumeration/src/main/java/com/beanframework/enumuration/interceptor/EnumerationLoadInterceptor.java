package com.beanframework.enumuration.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationLoadInterceptor implements LoadInterceptor<Enumeration> {

	@Override
	public void onLoad(Enumeration model) throws InterceptorException {
	}

}
