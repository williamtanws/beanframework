package com.beanframework.employee.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.employee.domain.Employee;

public class EmployeeValidateInterceptor extends AbstractValidateInterceptor<Employee> {

	@Override
	public void onValidate(Employee model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
