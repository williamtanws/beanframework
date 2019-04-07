package com.beanframework.user.interceptor.userauthority;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

public class UserAuthorityLoadInterceptor extends AbstractLoadInterceptor<UserAuthority> {

	@Override
	public void onLoad(UserAuthority model, InterceptorContext context) throws InterceptorException {

		if (context.isFetchable(UserAuthority.class, UserAuthority.USER_PERMISSION)) {
			Hibernate.initialize(model.getUserPermission());
			for (UserPermissionField field : model.getUserPermission().getFields()) {
				Hibernate.initialize(field.getDynamicFieldSlot());
				if (field.getDynamicFieldSlot() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
				if (field.getDynamicFieldSlot().getDynamicField() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
			}
		}

		if (context.isFetchable(UserAuthority.class, UserAuthority.USER_RIGHT)) {
			Hibernate.initialize(model.getUserRight());
			for (UserRightField field : model.getUserRight().getFields()) {
				Hibernate.initialize(field.getDynamicFieldSlot());
				if (field.getDynamicFieldSlot() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
				if (field.getDynamicFieldSlot().getDynamicField() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
			}
		}
		super.onLoad(model, context);
	}

}
