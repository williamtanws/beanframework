package com.beanframework.enumuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationPrepareInterceptor extends AbstractPrepareInterceptor<Enumeration> {

	@Override
	public void onPrepare(Enumeration model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}

}
