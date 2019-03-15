package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public interface PrepareInterceptor<MODEL> extends Interceptor {
	void onPrepare(MODEL model, InterceptorContext context) throws InterceptorException;
}
