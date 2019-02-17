package com.beanframework.dynamicfield.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldLoadInterceptor implements LoadInterceptor<DynamicField> {

	@Override
	public void onLoad(DynamicField model, InterceptorContext context) throws InterceptorException {
	}

}
