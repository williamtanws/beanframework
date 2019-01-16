package com.beanframework.user.interceptor.usergroup;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserRightField;

public class UserGroupLoadInterceptor implements LoadInterceptor<UserGroup> {

	@Override
	public void onLoad(UserGroup model) throws InterceptorException {

		Hibernate.initialize(model.getUserAuthorities());

		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());
			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
				Hibernate.initialize(userAuthority.getUserRight());
				for (UserRightField userRightField : userAuthority.getUserRight().getFields()) {
					Hibernate.initialize(userRightField.getDynamicField().getValues());
				}
				Hibernate.initialize(userAuthority.getUserPermission());
			}
			initializeUserGroups(userGroup);
		}
		
		for (UserGroupField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getValues());
		}
	}

	private void initializeUserGroups(UserGroup model) {
		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());
			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
				Hibernate.initialize(userAuthority.getUserRight());
				for (UserRightField userRightField : userAuthority.getUserRight().getFields()) {
					Hibernate.initialize(userRightField.getDynamicField().getValues());
				}
				Hibernate.initialize(userAuthority.getUserPermission());
			}
			initializeUserGroups(userGroup);
		}
	}
}
