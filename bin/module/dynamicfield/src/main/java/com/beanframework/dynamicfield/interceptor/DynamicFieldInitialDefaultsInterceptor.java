package com.beanframework.dynamicfield.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<DynamicField> {

	@Override
	public void onInitialDefaults(DynamicField model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
