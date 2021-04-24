package com.beanframework.core.interceptor.dynamicfieldtemplate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplatePrepareInterceptor extends AbstractPrepareInterceptor<DynamicFieldTemplate> {

	@Override
	public void onPrepare(DynamicFieldTemplate model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}

}
