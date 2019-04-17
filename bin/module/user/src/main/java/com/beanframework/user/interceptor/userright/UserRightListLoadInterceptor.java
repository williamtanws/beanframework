package com.beanframework.user.interceptor.userright;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class UserRightListLoadInterceptor extends AbstractLoadInterceptor<UserRight> {

	@Override
	public void onLoad(UserRight model, InterceptorContext context) throws InterceptorException {

		Hibernate.initialize(model.getFields());
		for (UserRightField field : model.getFields()) {
			if (field.getDynamicFieldSlot() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
			if (field.getDynamicFieldSlot().getDynamicField() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());

		}

		UserRight prototype = new UserRight();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setSort(model.getSort());
		prototype.setFields(model.getFields());

		model = prototype;
	}
}
