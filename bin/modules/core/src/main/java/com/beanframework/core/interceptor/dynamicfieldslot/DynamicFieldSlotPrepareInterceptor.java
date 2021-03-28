package com.beanframework.core.interceptor.dynamicfieldslot;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotPrepareInterceptor extends AbstractPrepareInterceptor<DynamicFieldSlot> {

	@Override
	public void onPrepare(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}

}
