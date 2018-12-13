package com.beanframework.common.interceptor;

import com.beanframework.common.exception.InterceptorException;

public interface ValidateInterceptor<MODEL> extends Interceptor {
	void onLoad(MODEL model) throws InterceptorException;
}
