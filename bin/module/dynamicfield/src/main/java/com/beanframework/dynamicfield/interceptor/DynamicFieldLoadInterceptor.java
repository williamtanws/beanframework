package com.beanframework.dynamicfield.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldLoadInterceptor extends AbstractLoadInterceptor<DynamicField> {

	@Override
	public DynamicField onLoad(DynamicField model, InterceptorContext context) throws InterceptorException {

		Hibernate.initialize(model.getLanguage());
		Hibernate.initialize(model.getEnumerations());

		DynamicField prototype = new DynamicField();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setRequired(model.getRequired());
		prototype.setRule(model.getRule());
		prototype.setType(model.getType());
		prototype.setLabel(model.getLabel());
		prototype.setGrid(model.getGrid());
		prototype.setLanguage(model.getLanguage());
		prototype.setEnumerations(model.getEnumerations());
		
		return prototype;
	}

}
