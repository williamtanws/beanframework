package com.beanframework.dynamicfield.interceptor.template;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateListLoadInterceptor extends AbstractLoadInterceptor<DynamicFieldTemplate> {

	@Override
	public void onLoad(DynamicFieldTemplate model, InterceptorContext context) throws InterceptorException {
		DynamicFieldTemplate prototype = new DynamicFieldTemplate();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		
		model = prototype;
	}

}
