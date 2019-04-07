package com.beanframework.dynamicfield.interceptor.slot;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<DynamicFieldSlot> {

	@Override
	public void onInitialDefaults(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
