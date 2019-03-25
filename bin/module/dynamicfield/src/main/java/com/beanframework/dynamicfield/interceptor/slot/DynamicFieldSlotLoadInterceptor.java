package com.beanframework.dynamicfield.interceptor.slot;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotLoadInterceptor extends AbstractLoadInterceptor<DynamicFieldSlot> {

	@Override
	public void onLoad(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {

		if (context.isFetchable(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD))
			Hibernate.initialize(model.getDynamicField());

		super.onLoad(model, context);
	}

}
