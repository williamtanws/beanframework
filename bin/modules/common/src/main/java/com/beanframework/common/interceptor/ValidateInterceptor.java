package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public interface ValidateInterceptor<MODEL> extends Interceptor {
	void onValidate(MODEL model, InterceptorContext context) throws InterceptorException;
}
