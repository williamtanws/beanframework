package com.beanframework.user.interceptor.userpermission;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class UserPermissionListLoadInterceptor extends AbstractLoadInterceptor<UserPermission> {

	@Override
	public UserPermission onLoad(UserPermission model, InterceptorContext context) throws InterceptorException {

		Hibernate.initialize(model.getFields());
		for (UserPermissionField field : model.getFields()) {
			if (field.getDynamicFieldSlot() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
			if (field.getDynamicFieldSlot().getDynamicField() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
		}

		UserPermission prototype = new UserPermission();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setSort(model.getSort());
		prototype.setFields(model.getFields());

		return prototype;
	}
}
