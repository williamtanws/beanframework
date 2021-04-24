package com.beanframework.core.interceptor.dynamicfieldtemplate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<DynamicFieldTemplate> {

	@Override
	public void onInitialDefaults(DynamicFieldTemplate model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
