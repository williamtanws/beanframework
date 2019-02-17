package com.beanframework.dynamicfield.interceptor.template;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateInitializeInterceptor implements InitializeInterceptor<DynamicFieldTemplate> {

	@Override
	public void onInitialize(DynamicFieldTemplate model, InterceptorContext context) throws InterceptorException {
		if (model.getDynamicFieldSlots() != null && model.getDynamicFieldSlots().isEmpty() == false)
			for (DynamicFieldSlot dynamicFieldSlot : model.getDynamicFieldSlots()) {
				Hibernate.initialize(dynamicFieldSlot.getDynamicField());
				if (dynamicFieldSlot.getDynamicField() != null)
					Hibernate.initialize(dynamicFieldSlot.getDynamicField().getEnumerations());

			}
	}

}
