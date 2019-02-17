package com.beanframework.dynamicfield.interceptor.slot;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotInitializeInterceptor implements InitializeInterceptor<DynamicFieldSlot> {

	@Override
	public void onInitialize(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {
		Hibernate.initialize(model.getDynamicField());
	}

}
