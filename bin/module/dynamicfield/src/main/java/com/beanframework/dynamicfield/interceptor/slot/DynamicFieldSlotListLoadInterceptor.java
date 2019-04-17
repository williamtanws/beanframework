package com.beanframework.dynamicfield.interceptor.slot;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotListLoadInterceptor extends AbstractLoadInterceptor<DynamicFieldSlot> {

	@Override
	public DynamicFieldSlot onLoad(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {
		DynamicFieldSlot prototype = new DynamicFieldSlot();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setSort(model.getSort());
		
		return prototype;
	}

}
