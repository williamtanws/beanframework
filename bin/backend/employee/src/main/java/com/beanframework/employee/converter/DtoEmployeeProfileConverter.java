package com.beanframework.employee.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;

@Component
public class DtoEmployeeProfileConverter implements Converter<Employee, Employee> {

	@Autowired
	private EmployeeService employeeService;

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
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		return prototype;
	}

}
