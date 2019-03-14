package com.beanframework.dynamicfield.interceptor.slot;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotRemoveInterceptor implements RemoveInterceptor<DynamicFieldSlot> {

	@Override
	public void onRemove(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {
	}

}