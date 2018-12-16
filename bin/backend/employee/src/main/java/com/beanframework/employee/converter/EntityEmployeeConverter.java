package com.beanframework.employee.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class EntityEmployeeConverter implements EntityConverter<Employee, Employee> {

	@Autowired
	private ModelService modelService;

	@Override
	public Employee convert(Employee source) {

		Employee prototype = modelService.create(Employee.class);
		if (source.getUuid() != null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Employee.UUID, source.getUuid());
			Employee exists = modelService.findOneEntityByProperties(properties, Employee.class);

			if (exists != null) {
				prototype = exists;
			}
		}

		return convert(source, prototype);
	}

	private Employee convert(Employee source, Employee prototype) {

		prototype.setId(source.getId());
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
			if (userGroup.getUuid() != null) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserGroup.UUID, source.getUuid());
				UserGroup existingUserGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
				
				if (existingUserGroup != null) {
					prototype.getUserGroups().add(existingUserGroup);
				}
			} else if (StringUtils.isNotEmpty(userGroup.getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserGroup.ID, source.getId());
				UserGroup existingUserGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
				
				if (existingUserGroup != null) {
					prototype.getUserGroups().add(existingUserGroup);
				}
			}
		}

		return prototype;
	}

}
