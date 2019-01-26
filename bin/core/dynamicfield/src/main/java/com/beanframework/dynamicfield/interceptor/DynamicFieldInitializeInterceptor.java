package com.beanframework.dynamicfield.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldInitializeInterceptor implements InitializeInterceptor<DynamicField> {

	@Override
	public void onInitialize(DynamicField model) throws InterceptorException {
		Hibernate.initialize(model.getEnums());
	}

}