package com.beanframework.enumuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationRemoveInterceptor implements RemoveInterceptor<Enumeration> {

	@Override
	public void onRemove(Enumeration model, InterceptorContext context) throws InterceptorException {
	}

}
