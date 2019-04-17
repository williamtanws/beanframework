package com.beanframework.dynamicfield.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldListLoadInterceptor extends AbstractLoadInterceptor<DynamicField> {

	@Override
	public void onLoad(DynamicField model, InterceptorContext context) throws InterceptorException {
		DynamicField prototype = new DynamicField();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setRequired(model.getRequired());
		prototype.setRule(model.getRule());
		prototype.setType(model.getType());
		prototype.setLabel(model.getLabel());
		prototype.setGrid(model.getGrid());
		
		model = prototype;
	}

}
