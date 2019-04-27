package com.beanframework.menu.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

public class MenuLoadInterceptor extends AbstractLoadInterceptor<Menu> {

	@Override
	public Menu onLoad(Menu model, InterceptorContext context) throws InterceptorException {

		Hibernate.initialize(model.getChilds());
		for (Menu menu : model.getChilds()) {
			initializeChilds(menu);
		}

		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());

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
					Hibernate.initialize(field.getDynamicFieldSlot());
					if (field.getDynamicFieldSlot() != null)
						Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
					if (field.getDynamicFieldSlot().getDynamicField() != null)
						Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
				}
			}
		}

		Hibernate.initialize(model.getFields());
		for (MenuField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicFieldSlot());
			if (field.getDynamicFieldSlot() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
			if (field.getDynamicFieldSlot().getDynamicField() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getLanguage());
			if (field.getDynamicFieldSlot().getDynamicField() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());

		}

		Menu prototype = new Menu();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setParent(model.getParent());
		prototype.setIcon(model.getIcon());
		prototype.setPath(model.getPath());
		prototype.setSort(model.getSort());
		prototype.setTarget(model.getTarget());
		prototype.setEnabled(model.getEnabled());

		prototype.setChilds(model.getChilds());
		prototype.setUserGroups(model.getUserGroups());

		prototype.setFields(model.getFields());

		return prototype;
	}

	private void initializeChilds(Menu model) {
		Hibernate.initialize(model.getParent());

		Hibernate.initialize(model.getChilds());
		for (Menu menu : model.getChilds()) {
			initializeChilds(menu);
		}

		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());

			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
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
		}

		Hibernate.initialize(model.getFields());
		for (MenuField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicFieldSlot());
			if (field.getDynamicFieldSlot() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
			if (field.getDynamicFieldSlot().getDynamicField() != null)
				Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
		}
	}

}
