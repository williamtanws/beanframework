package com.beanframework.employee.converter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;
import com.beanframework.user.utils.PasswordUtils;

@Component
public class EntityEmployeeConverter implements Converter<Employee, Employee> {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private UserGroupService userGroupService;

	@Override
	public Employee convert(Employee source) {

		Optional<Employee> prototype = Optional.of(employeeService.create());
		if (source.getUuid() != null) {
			Optional<Employee> exists = employeeService.findEntityByUuid(source.getUuid());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}
		else if (StringUtils.isNotEmpty(source.getId())) {
			Optional<Employee> exists = employeeService.findEntityById(source.getId());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}

		return convert(source, prototype.get());
	}

	private Employee convert(Employee source, Employee prototype) {

		prototype.setId(source.getId());
		prototype.setName(source.getName());
		prototype.setAccountNonExpired(source.isAccountNonExpired());
		prototype.setAccountNonLocked(source.isAccountNonLocked());
		prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
		prototype.setEnabled(source.isEnabled());
		prototype.setLastModifiedDate(new Date());

		if (StringUtils.isNotEmpty(source.getPassword())) {
			prototype.setPassword(PasswordUtils.encode(source.getPassword()));
		}

		Hibernate.initialize(prototype.getUserGroups());
		prototype.getUserGroups().clear();
		for (UserGroup userGroup : source.getUserGroups()) {
			if(userGroup.getUuid() != null) {
				Optional<UserGroup> existingUserGroup = userGroupService.findEntityByUuid(userGroup.getUuid());
				if (existingUserGroup.isPresent()) {
					prototype.getUserGroups().add(existingUserGroup.get());
				}
			}
			else if(StringUtils.isNotEmpty(userGroup.getId())) {
				Optional<UserGroup> existingUserGroup = userGroupService.findEntityById(userGroup.getId());
				if (existingUserGroup.isPresent()) {
					prototype.getUserGroups().add(existingUserGroup.get());
				}
			}
		}

		return prototype;
	}

}
