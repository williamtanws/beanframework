package com.beanframework.core.interceptor.dynamicfield;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldPrepareInterceptor extends AbstractPrepareInterceptor<DynamicField> {

	@Override
	public void onPrepare(DynamicField model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}

}
