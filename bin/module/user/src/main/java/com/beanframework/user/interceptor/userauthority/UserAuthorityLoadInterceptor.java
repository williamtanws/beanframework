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
		Hibernate.initialize(model.getUserPermission());
		for (UserPermissionField field : model.getUserPermission().getFields()) {
			Hibernate.initialize(field.getDynamicFieldSlot());
			if (field.getDynamicFieldSlot() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
			if (field.getDynamicFieldSlot().getDynamicField() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
		}

		Hibernate.initialize(model.getUserRight());
		for (UserRightField field : model.getUserRight().getFields()) {
			Hibernate.initialize(field.getDynamicFieldSlot());
			if (field.getDynamicFieldSlot() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
			if (field.getDynamicFieldSlot().getDynamicField() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
		}

		UserAuthority prototype = new UserAuthority();
		loadCommonProperties(model, prototype, context);
		prototype.setEnabled(model.getEnabled());
		prototype.setUserPermission(model.getUserPermission());
		prototype.setUserRight(model.getUserRight());

		model = prototype;
	}

}
