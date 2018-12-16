package com.beanframework.employee.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.converter.DtoUserGroupConverter;

public class DtoEmployeeConverter implements DtoConverter<Employee, Employee> {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DtoUserGroupConverter dtoUserGroupConverter;

	@Override
	public Employee convert(Employee source) {
		return convert(source, modelService.create(Employee.class));
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
