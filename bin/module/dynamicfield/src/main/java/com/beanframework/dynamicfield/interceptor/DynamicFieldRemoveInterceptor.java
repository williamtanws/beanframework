package com.beanframework.dynamicfield.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldRemoveInterceptor extends AbstractRemoveInterceptor<DynamicField> {

	@Override
	public void onRemove(DynamicField model, InterceptorContext context) throws InterceptorException {
	}

}
