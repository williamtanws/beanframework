package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public abstract class AbstractLoadInterceptor<T> implements LoadInterceptor<T> {
	
	@Override
	public void onLoad(T model, InterceptorContext context) throws InterceptorException {
		
	}
}
