package com.beanframework.employee.converter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.user.utils.PasswordUtils;

@Component
public class EntityEmployeeProfileConverter implements EntityConverter<Employee, Employee> {

	@Autowired
	private EmployeeService employeeService;

	@Override
	public Employee convert(Employee source) {

		Optional<Employee> prototype = employeeService.findEntityByUuid(source.getUuid());

		return convert(source, prototype.get());
	}

	private Employee convert(Employee source, Employee prototype) {

		prototype.setLastModifiedDate(new Date());

		if (StringUtils.isNotEmpty(source.getPassword())) {
			prototype.setPassword(PasswordUtils.encode(source.getPassword()));
		}
		
		return prototype;
	}

}
