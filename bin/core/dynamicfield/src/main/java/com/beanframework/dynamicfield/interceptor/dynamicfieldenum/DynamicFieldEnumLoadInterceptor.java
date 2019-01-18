package com.beanframework.dynamicfield.interceptor.dynamicfieldenum;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public class DynamicFieldEnumLoadInterceptor implements LoadInterceptor<DynamicFieldEnum> {

	@Override
	public void onLoad(DynamicFieldEnum model) throws InterceptorException {
		Hibernate.initialize(model.getDynamicField());
	}

}
