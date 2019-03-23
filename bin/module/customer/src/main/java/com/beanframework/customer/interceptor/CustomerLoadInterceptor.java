package com.beanframework.customer.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

public class CustomerLoadInterceptor extends AbstractLoadInterceptor<Customer> {

	@Override
	public void onLoad(Customer model, InterceptorContext context) throws InterceptorException {
		super.onLoad(model, context);

		if (context.getFetchProperties().contains(Customer.USER_GROUPS)) {
			Hibernate.initialize(model.getUserGroups());
			for (UserGroup userGroup : model.getUserGroups()) {
				Hibernate.initialize(userGroup.getUserAuthorities());
				for (UserGroupField field : userGroup.getFields()) {
					Hibernate.initialize(field.getDynamicField());
					if (field.getDynamicField() != null)
						Hibernate.initialize(field.getDynamicField().getEnumerations());
				}
				for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
					Hibernate.initialize(userAuthority.getUserRight());
					for (UserRightField field : userAuthority.getUserRight().getFields()) {
						Hibernate.initialize(field.getDynamicField());
						if (field.getDynamicField() != null)
							Hibernate.initialize(field.getDynamicField().getEnumerations());
					}
					Hibernate.initialize(userAuthority.getUserPermission());
					for (UserPermissionField field : userAuthority.getUserPermission().getFields()) {
						Hibernate.initialize(field.getDynamicField());
						if (field.getDynamicField() != null)
							Hibernate.initialize(field.getDynamicField().getEnumerations());
					}
				}
				initializeUserGroups(userGroup);
			}
		}

		if (context.getFetchProperties().contains(Customer.FIELDS)) {
			Hibernate.initialize(model.getFields());
			for (UserField field : model.getFields()) {
				Hibernate.initialize(field.getDynamicField());
				if (field.getDynamicField() != null)
					Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
		}

	}

	private void initializeUserGroups(UserGroup model) {
		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());
			for (UserGroupField field : userGroup.getFields()) {
				Hibernate.initialize(field.getDynamicField());
				if (field.getDynamicField() != null)
					Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
				Hibernate.initialize(userAuthority.getUserRight());
				for (UserRightField field : userAuthority.getUserRight().getFields()) {
					Hibernate.initialize(field.getDynamicField());
					if (field.getDynamicField() != null)
						Hibernate.initialize(field.getDynamicField().getEnumerations());
				}
				Hibernate.initialize(userAuthority.getUserPermission());
				for (UserPermissionField field : userAuthority.getUserPermission().getFields()) {
					Hibernate.initialize(field.getDynamicField());
					if (field.getDynamicField() != null)
						Hibernate.initialize(field.getDynamicField().getEnumerations());
				}
			}
			initializeUserGroups(userGroup);
		}
	}

}
