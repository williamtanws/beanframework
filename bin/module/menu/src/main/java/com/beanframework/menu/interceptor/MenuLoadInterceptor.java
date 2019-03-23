package com.beanframework.menu.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

public class MenuLoadInterceptor extends AbstractLoadInterceptor<Menu> {

	@Override
	public void onLoad(Menu model, InterceptorContext context) throws InterceptorException {
		super.onLoad(model, context);

		if (context.getFetchProperties().contains(Menu.CHILDS)) {
			Hibernate.initialize(model.getChilds());
			for (Menu menu : model.getChilds()) {
				initializeChilds(menu);
			}
		}

		if (context.getFetchProperties().contains(Menu.USER_GROUPS)) {
			Hibernate.initialize(model.getUserGroups());
			for (UserGroup userGroup : model.getUserGroups()) {
				Hibernate.initialize(userGroup.getUserAuthorities());
				for (UserGroupField field : userGroup.getFields()) {
					Hibernate.initialize(field.getDynamicField().getEnumerations());
				}
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
				initializeUserGroups(userGroup);
			}
		}

		if (context.getFetchProperties().contains(Menu.FIELDS)) {
			Hibernate.initialize(model.getFields());
			for (MenuField field : model.getFields()) {
				Hibernate.initialize(field.getDynamicField());
				Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
		}
	}

	private void initializeChilds(Menu model) {
		Hibernate.initialize(model.getParent());
//		if (model.getParent() != null) {
//			initializeParent(model.getParent());
//		}

		Hibernate.initialize(model.getChilds());
		for (Menu menu : model.getChilds()) {
			initializeChilds(menu);
		}

		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());
			for (UserGroupField field : userGroup.getFields()) {
				Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
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
			initializeUserGroups(userGroup);
		}

		Hibernate.initialize(model.getFields());
		for (MenuField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField());
			Hibernate.initialize(field.getDynamicField().getEnumerations());
		}
	}

	private void initializeUserGroups(UserGroup model) {
		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());
			for (UserGroupField field : userGroup.getFields()) {
				Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
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
			initializeUserGroups(userGroup);
		}
	}

}
