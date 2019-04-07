package com.beanframework.enumuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationRemoveInterceptor extends AbstractRemoveInterceptor<Enumeration> {

	@Override
	public void onRemove(Enumeration model, InterceptorContext context) throws InterceptorException {
		super.onRemove(model, context);
	}

}
