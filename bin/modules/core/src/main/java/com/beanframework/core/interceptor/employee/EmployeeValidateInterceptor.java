package com.beanframework.core.interceptor.employee;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.Employee;

public class EmployeeValidateInterceptor extends AbstractValidateInterceptor<Employee> {

	@Override
	public void onValidate(Employee model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
