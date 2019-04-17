package com.beanframework.dynamicfield.interceptor.slot;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotLoadInterceptor extends AbstractLoadInterceptor<DynamicFieldSlot> {

	@Override
	public DynamicFieldSlot onLoad(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {

		Hibernate.initialize(model.getDynamicField());

		DynamicFieldSlot prototype = new DynamicFieldSlot();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setSort(model.getSort());
		prototype.setDynamicField(model.getDynamicField());
		
		return prototype;
	}

}
