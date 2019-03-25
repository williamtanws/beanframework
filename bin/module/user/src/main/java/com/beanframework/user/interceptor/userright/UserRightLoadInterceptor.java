package com.beanframework.user.interceptor.userright;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class UserRightLoadInterceptor extends AbstractLoadInterceptor<UserRight> {

	@Override
	public void onLoad(UserRight model, InterceptorContext context) throws InterceptorException {

		if (context.isFetchable(UserRight.class, UserRight.FIELDS)) {
			Hibernate.initialize(model.getFields());
			for (UserRightField field : model.getFields()) {
				Hibernate.initialize(field.getDynamicField());
				if (field.getDynamicField() != null)
					Hibernate.initialize(field.getDynamicField().getEnumerations());

			}
		}
		super.onLoad(model, context);
	}
}
