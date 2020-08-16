package com.beanframework.core.interceptor.employee;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.Employee;

public class EmployeeLoadInterceptor extends AbstractLoadInterceptor<Employee> {

	@Override
	public void onLoad(Employee model, InterceptorContext context) throws InterceptorException {
	}

}
