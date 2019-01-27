package com.beanframework.common.interceptor;

import com.beanframework.common.exception.InterceptorException;

public interface InitialDefaultsInterceptor<MODEL> extends Interceptor {
	void onInitialDefaults(MODEL model) throws InterceptorException;
}
