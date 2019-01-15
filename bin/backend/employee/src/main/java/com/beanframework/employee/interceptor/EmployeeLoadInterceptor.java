package com.beanframework.employee.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserRightField;

public class EmployeeLoadInterceptor implements LoadInterceptor<Employee> {

	@Override
	public void onLoad(Employee model) throws InterceptorException {
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
