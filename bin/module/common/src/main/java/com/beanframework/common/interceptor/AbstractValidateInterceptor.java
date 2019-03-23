package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;

public abstract class AbstractValidateInterceptor<T> implements ValidateInterceptor<T> {
	
	@Override
	public void onValidate(T model, InterceptorContext context) throws InterceptorException {
		
	}
}
