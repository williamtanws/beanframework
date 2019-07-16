package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public abstract class AbstractRemoveInterceptor<T> implements RemoveInterceptor<T> {
	
	@Override
	public void onRemove(T model, InterceptorContext context) throws InterceptorException {
	}
}
