package com.beanframework.dynamicfield.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldRemoveInterceptor implements RemoveInterceptor<DynamicField> {

	@Override
	public void onRemove(DynamicField model, InterceptorContext context) throws InterceptorException {
	}

}
