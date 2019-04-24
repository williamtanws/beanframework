package com.beanframework.employee.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.employee.domain.Employee;

public class EmployeeListLoadInterceptor extends AbstractLoadInterceptor<Employee> {

	@Override
	public Employee onLoad(Employee model, InterceptorContext context) throws InterceptorException {
		Employee prototype = new Employee();
		loadCommonProperties(model, prototype, context);
		prototype.setType(model.getType());
		prototype.setPassword(model.getPassword());
		prototype.setAccountNonExpired(model.getAccountNonExpired());
		prototype.setAccountNonLocked(model.getAccountNonLocked());
		prototype.setCredentialsNonExpired(model.getCredentialsNonExpired());
		prototype.setEnabled(model.getEnabled());
		prototype.setName(model.getName());

		return prototype;
	}

}
