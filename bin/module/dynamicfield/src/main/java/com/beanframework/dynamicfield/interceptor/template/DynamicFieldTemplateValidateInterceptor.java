package com.beanframework.dynamicfield.interceptor.template;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateValidateInterceptor extends AbstractValidateInterceptor<DynamicFieldTemplate> {

	@Override
	public void onValidate(DynamicFieldTemplate model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
