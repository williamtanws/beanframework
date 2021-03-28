package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public abstract class AbstractPrepareInterceptor<T> implements PrepareInterceptor<T> {
	
	@Override
	public void onPrepare(T model, InterceptorContext context) throws InterceptorException {
		
	}
}
