package com.beanframework.common.interceptor;

import com.beanframework.common.exception.InterceptorException;

public interface PrepareInterceptor<MODEL> extends Interceptor {
	void onLoad(MODEL model) throws InterceptorException;
}
