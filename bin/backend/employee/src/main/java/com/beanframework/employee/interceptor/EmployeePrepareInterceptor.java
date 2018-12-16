package com.beanframework.employee.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.employee.domain.Employee;

public class EmployeePrepareInterceptor implements PrepareInterceptor<Employee> {

	@Override
	public void onPrepare(Employee model) throws InterceptorException {
		
	}

}
