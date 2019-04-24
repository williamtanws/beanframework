package com.beanframework.dynamicfield.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.language.domain.Language;

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
		prototype.setLanguage((Language) Hibernate.unproxy(model.getLanguage()));
		for (Enumeration enumeration : model.getEnumerations()) {
			prototype.getEnumerations().add((Enumeration) Hibernate.unproxy(enumeration));
		}
		return prototype;
	}

}
