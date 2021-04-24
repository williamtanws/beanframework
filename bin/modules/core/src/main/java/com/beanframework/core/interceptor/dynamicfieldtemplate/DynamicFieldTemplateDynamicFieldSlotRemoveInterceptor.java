package com.beanframework.core.interceptor.dynamicfieldtemplate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldTemplateDynamicFieldSlotRemoveInterceptor extends AbstractRemoveInterceptor<DynamicFieldSlot> {
	
	@Override
	public void onRemove(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {
	}
}
