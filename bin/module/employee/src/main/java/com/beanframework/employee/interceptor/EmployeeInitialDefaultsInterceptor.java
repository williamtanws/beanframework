package com.beanframework.employee.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.employee.domain.Employee;

public class EmployeeInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Employee> {

	@Override
	public void onInitialDefaults(Employee model) throws InterceptorException {
		model.setEnabled(true);
		model.setAccountNonExpired(true);
		model.setAccountNonLocked(true);
		model.setCredentialsNonExpired(true);
	}

}
