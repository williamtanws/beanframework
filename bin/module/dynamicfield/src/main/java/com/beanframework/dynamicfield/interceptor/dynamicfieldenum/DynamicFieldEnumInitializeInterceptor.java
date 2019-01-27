package com.beanframework.dynamicfield.interceptor.dynamicfieldenum;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public class DynamicFieldEnumInitializeInterceptor implements InitializeInterceptor<DynamicFieldEnum> {

	@Override
	public void onInitialize(DynamicFieldEnum model) throws InterceptorException {
		Hibernate.initialize(model.getDynamicField());
	}

}
