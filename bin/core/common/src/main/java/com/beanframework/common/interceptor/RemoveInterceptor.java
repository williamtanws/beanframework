package com.beanframework.common.interceptor;

import com.beanframework.common.exception.InterceptorException;

public interface RemoveInterceptor<MODEL> extends Interceptor {
	void onRemove(MODEL model) throws InterceptorException;
}