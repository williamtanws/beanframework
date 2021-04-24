package com.beanframework.core.interceptor.dynamicfieldtemplate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateRemoveInterceptor extends AbstractRemoveInterceptor<DynamicFieldTemplate> {

	@Override
	public void onRemove(DynamicFieldTemplate model, InterceptorContext context) throws InterceptorException {
	}

}
