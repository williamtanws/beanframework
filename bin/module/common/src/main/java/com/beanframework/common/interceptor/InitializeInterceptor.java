package com.beanframework.common.interceptor;

import com.beanframework.common.exception.InterceptorException;

public interface InitializeInterceptor<MODEL> extends Interceptor {
	void onInitialize(MODEL model) throws InterceptorException;
}
