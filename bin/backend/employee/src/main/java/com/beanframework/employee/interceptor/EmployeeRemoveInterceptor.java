package com.beanframework.employee.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.employee.domain.Employee;

public class EmployeeRemoveInterceptor implements RemoveInterceptor<Employee> {

	@Override
	public void onRemove(Employee model) throws InterceptorException {
	}

}
