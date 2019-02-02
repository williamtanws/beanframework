package com.beanframework.user.interceptor.usergroup;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

public class UserGroupInitializeInterceptor implements InitializeInterceptor<UserGroup> {

	@Override
	public void onInitialize(UserGroup model) throws InterceptorException {

		Hibernate.initialize(model.getUserAuthorities());
		for (UserAuthority userAuthority : model.getUserAuthorities()) {
			Hibernate.initialize(userAuthority.getUserRight());
			for (UserRightField field : userAuthority.getUserRight().getFields()) {
				Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
			Hibernate.initialize(userAuthority.getUserPermission());
			for (UserPermissionField field : userAuthority.getUserPermission().getFields()) {
				Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
		}

		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());
			for (UserGroupField field : userGroup.getFields()) {
				Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
				Hibernate.initialize(userAuthority.getUserRight());
				for (UserRightField field : userAuthority.getUserRight().getFields()) {
					Hibernate.initialize(field.getDynamicField().getEnumerations());
				}
				Hibernate.initialize(userAuthority.getUserPermission());
				for (UserPermissionField field : userAuthority.getUserPermission().getFields()) {
					Hibernate.initialize(field.getDynamicField().getEnumerations());
				}
			}
		}

		Hibernate.initialize(model.getFields());
		for (UserGroupField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnumerations());
		}
	}
}
