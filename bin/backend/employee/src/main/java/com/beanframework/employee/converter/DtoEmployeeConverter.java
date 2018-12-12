package com.beanframework.employee.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.user.converter.DtoUserGroupConverter;

@Component
public class DtoEmployeeConverter implements Converter<Employee, Employee> {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DtoUserGroupConverter dtoUserGroupConverter;

	@Override
	public Employee convert(Employee source) {
		return convert(source, employeeService.create());
	}

	public List<Employee> convert(List<Employee> sources) {
		List<Employee> convertedList = new ArrayList<Employee>();
		for (Employee source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Employee convert(Employee source, Employee prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setAccountNonExpired(source.isAccountNonExpired());
		prototype.setAccountNonLocked(source.isAccountNonLocked());
		prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
		prototype.setEnabled(source.isEnabled());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		
		Hibernate.initialize(source.getUserGroups());
		prototype.setUserGroups(dtoUserGroupConverter.convert(source.getUserGroups()));

		return prototype;
	}

}
