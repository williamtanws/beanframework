package com.beanframework.menu.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserRightField;

public class MenuLoadInterceptor implements LoadInterceptor<Menu> {

	@Override
	public void onLoad(Menu model) throws InterceptorException {

		Hibernate.initialize(model.getParent());
		if (model.getParent() != null) {
			initializeParent(model.getParent());
		}

		Hibernate.initialize(model.getChilds());
		for (Menu menu : model.getChilds()) {
			initializeChilds(menu);
		}

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
		
		for (MenuField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getValues());
		}
	}

	private void initializeParent(Menu model) {
		Hibernate.initialize(model.getParent());
		if (model.getParent() != null) {
			initializeParent(model.getParent().getParent());
		}
	}

	private void initializeChilds(Menu model) {
		Hibernate.initialize(model.getChilds());
		for (Menu menu : model.getChilds()) {
			initializeChilds(menu);
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
