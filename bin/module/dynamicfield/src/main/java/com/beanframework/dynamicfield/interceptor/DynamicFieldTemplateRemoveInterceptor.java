package com.beanframework.dynamicfield.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateRemoveInterceptor implements RemoveInterceptor<DynamicFieldTemplate> {

	@Override
	public void onRemove(DynamicFieldTemplate model) throws InterceptorException {
	}

}