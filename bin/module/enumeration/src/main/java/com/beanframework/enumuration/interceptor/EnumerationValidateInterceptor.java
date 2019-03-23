package com.beanframework.enumuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationValidateInterceptor extends AbstractValidateInterceptor<Enumeration> {

	@Override
	public void onValidate(Enumeration model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
