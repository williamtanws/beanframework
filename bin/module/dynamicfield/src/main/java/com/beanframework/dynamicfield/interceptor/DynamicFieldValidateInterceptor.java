package com.beanframework.dynamicfield.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldValidateInterceptor extends AbstractValidateInterceptor<DynamicField> {

	@Override
	public void onValidate(DynamicField model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
