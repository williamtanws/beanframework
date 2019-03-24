package com.beanframework.vendor.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;
import com.beanframework.vendor.domain.Vendor;

public class VendorLoadInterceptor extends AbstractLoadInterceptor<Vendor> {

	@Override
	public void onLoad(Vendor model, InterceptorContext context) throws InterceptorException {

		if (context.isFetchable(Vendor.USER_GROUPS)) {
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

		if (context.isFetchable(Vendor.FIELDS)) {
			Hibernate.initialize(model.getFields());
			for (UserField field : model.getFields()) {
				Hibernate.initialize(field.getDynamicField());
				if (field.getDynamicField() != null)
					Hibernate.initialize(field.getDynamicField().getEnumerations());
			}
		}

		super.onLoad(model, context);

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
