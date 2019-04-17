package com.beanframework.enumuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationListLoadInterceptor extends AbstractLoadInterceptor<Enumeration> {

	@Override
	public void onLoad(Enumeration model, InterceptorContext context) throws InterceptorException {
		Enumeration prototype = new Enumeration();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setSort(model.getSort());
		
		model = prototype;
	}

}
