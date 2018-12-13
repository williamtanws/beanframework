package com.beanframework.employee.converter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.employee.domain.Employee;

@Component
public class DtoEmployeePrincipalConverter implements DtoConverter<Employee, Employee> {

	@Override
	public Employee convert(Employee source) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employeePrincipal = (Employee) auth.getPrincipal();
		
		employeePrincipal.setId(source.getId());
		
		return employeePrincipal;
	}

}
