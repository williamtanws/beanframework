package com.beanframework.dynamicfield.interceptor.template;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateLoadInterceptor extends AbstractLoadInterceptor<DynamicFieldTemplate> {

	@Override
	public void onLoad(DynamicFieldTemplate model, InterceptorContext context) throws InterceptorException {

		if (model.getDynamicFieldSlots() != null && model.getDynamicFieldSlots().isEmpty() == false)
			for (DynamicFieldSlot dynamicFieldSlot : model.getDynamicFieldSlots()) {
				Hibernate.initialize(dynamicFieldSlot.getDynamicField());
				if (dynamicFieldSlot.getDynamicField() != null)
					Hibernate.initialize(dynamicFieldSlot.getDynamicField().getEnumerations());

			}

		DynamicFieldTemplate prototype = new DynamicFieldTemplate();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setDynamicFieldSlots(model.getDynamicFieldSlots());
		
		model = prototype;
	}

}
