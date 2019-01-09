package com.beanframework.employee.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.employee.domain.Employee;

public class EmployeeLoadInterceptor implements LoadInterceptor<Employee> {

	@Override
	public void onLoad(Employee model) throws InterceptorException {
		Hibernate.initialize(model.getUserGroups());
	}

}
