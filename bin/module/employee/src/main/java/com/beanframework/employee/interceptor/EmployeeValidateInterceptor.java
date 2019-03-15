package com.beanframework.employee.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.employee.domain.Employee;

public class EmployeeValidateInterceptor implements ValidateInterceptor<Employee> {

	@Override
	public void onValidate(Employee model, InterceptorContext context) throws InterceptorException {

	}

}
