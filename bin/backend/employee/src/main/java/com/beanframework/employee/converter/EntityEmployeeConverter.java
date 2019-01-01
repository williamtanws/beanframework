package com.beanframework.employee.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class EntityEmployeeConverter implements EntityConverter<Employee, Employee> {

	@Autowired
	private ModelService modelService;

	@Override
	public Employee convert(Employee source) throws ConverterException {

		Employee prototype;
		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Employee.UUID, source.getUuid());
				Employee exists = modelService.findOneEntityByProperties(properties, Employee.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Employee.class);
				}
			} else {
				prototype = modelService.create(Employee.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private Employee convert(Employee source, Employee prototype) throws ConverterException {

		try {
			if (source.getId() != null)
				prototype.setId(source.getId());
			prototype.setLastModifiedDate(new Date());

			prototype.setAccountNonExpired(source.getAccountNonExpired());
			prototype.setAccountNonLocked(source.getAccountNonLocked());
			prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
			prototype.setEnabled(source.getEnabled());
			if (StringUtils.isNotBlank(source.getPassword()))
				prototype.setPassword(PasswordUtils.encode(source.getPassword()));
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserField sourceUserField : source.getFields()) {
						if (prototype.getFields().get(i).getUuid().equals(sourceUserField.getUuid())) {
							prototype.getFields().get(i).setValue(sourceUserField.getValue());
						}
					}
				}
			}
			if (source.getUserGroups() == null || source.getUserGroups() .isEmpty()) {
				prototype.setUserGroups(new ArrayList<UserGroup>());
			} else {
				List<UserGroup> childs = new ArrayList<UserGroup>();
				for (UserGroup userGroup : source.getUserGroups()) {
					UserGroup entityUserGroup = modelService.findOneEntityByUuid(userGroup.getUuid(), UserGroup.class);
					if (entityUserGroup != null)
						childs.add(entityUserGroup);
				}
				prototype.setUserGroups(childs);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
