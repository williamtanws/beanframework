package com.beanframework.enumuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationValidateInterceptor implements ValidateInterceptor<Enumeration> {

	@Override
	public void onValidate(Enumeration model, InterceptorContext context) throws InterceptorException {

	}
}
