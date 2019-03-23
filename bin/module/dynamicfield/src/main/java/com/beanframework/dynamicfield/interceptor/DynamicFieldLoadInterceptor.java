package com.beanframework.dynamicfield.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldLoadInterceptor extends AbstractLoadInterceptor<DynamicField> {

	@Override
	public void onLoad(DynamicField model, InterceptorContext context) throws InterceptorException {
		super.onLoad(model, context);

		if (context.getFetchProperties().contains(DynamicField.ENUMERATIONS))
			Hibernate.initialize(model.getEnumerations());
	}

}
