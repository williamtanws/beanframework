package com.beanframework.dynamicfield.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateInitializeInterceptor implements InitializeInterceptor<DynamicFieldTemplate> {

	@Override
	public void onInitialize(DynamicFieldTemplate model) throws InterceptorException {
		Hibernate.initialize(model.getDynamicFields());
	}

}
