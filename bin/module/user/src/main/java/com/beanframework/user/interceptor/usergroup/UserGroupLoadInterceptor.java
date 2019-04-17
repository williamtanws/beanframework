package com.beanframework.user.interceptor.usergroup;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

public class UserGroupLoadInterceptor extends AbstractLoadInterceptor<UserGroup> {

	@Override
	public UserGroup onLoad(UserGroup model, InterceptorContext context) throws InterceptorException {

		Hibernate.initialize(model.getUserAuthorities());
		for (UserAuthority userAuthority : model.getUserAuthorities()) {
			Hibernate.initialize(userAuthority.getUserRight());
			for (UserRightField field : userAuthority.getUserRight().getFields()) {
				if (field.getDynamicFieldSlot() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
				if (field.getDynamicFieldSlot().getDynamicField() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
			}
			Hibernate.initialize(userAuthority.getUserPermission());
			for (UserPermissionField field : userAuthority.getUserPermission().getFields()) {
				if (field.getDynamicFieldSlot() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
				if (field.getDynamicFieldSlot().getDynamicField() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
			}
		}

		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {

			Hibernate.initialize(userGroup.getUserAuthorities());
			for (UserGroupField field : userGroup.getFields()) {
				if (field.getDynamicFieldSlot() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
				if (field.getDynamicFieldSlot().getDynamicField() != null)
					Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
			}

			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {

				Hibernate.initialize(userAuthority.getUserRight());
				for (UserRightField field : userAuthority.getUserRight().getFields()) {
					Hibernate.initialize(field.getDynamicFieldSlot());
					if (field.getDynamicFieldSlot() != null)
						Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
					if (field.getDynamicFieldSlot().getDynamicField() != null)
						Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
				}

				Hibernate.initialize(userAuthority.getUserPermission());
				for (UserPermissionField field : userAuthority.getUserPermission().getFields()) {
					if (field.getDynamicFieldSlot() != null)
						Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
					if (field.getDynamicFieldSlot().getDynamicField() != null)
						Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
				}
			}
		}

		Hibernate.initialize(model.getFields());
		for (UserGroupField field : model.getFields()) {
			if (field.getDynamicFieldSlot() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
			if (field.getDynamicFieldSlot().getDynamicField() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
		}

		UserGroup prototype = new UserGroup();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setUsers(model.getUsers());
		prototype.setUserGroups(model.getUserGroups());
		prototype.setUserAuthorities(model.getUserAuthorities());
		prototype.setFields(model.getFields());

		return prototype;

	}
}
