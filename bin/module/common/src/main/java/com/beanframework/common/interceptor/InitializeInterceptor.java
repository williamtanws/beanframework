package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public interface InitializeInterceptor<MODEL> extends Interceptor {
	void onInitialize(MODEL model, InterceptorContext context) throws InterceptorException;
}
