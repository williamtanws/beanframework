package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.EmployeeCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

@Component
public class EntityEmployeeImporterConverter implements EntityConverter<EmployeeCsv, Employee> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityEmployeeImporterConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Employee convert(EmployeeCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Employee.ID, source.getId());

				Employee prototype = modelService.findOneEntityByProperties(properties, Employee.class);

				if (prototype != null) {
					Hibernate.initialize(prototype.getUserGroups());

					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(Employee.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}
	}

	private Employee convert(EmployeeCsv source, Employee prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.strip(source.getId()));

			prototype.setAccountNonExpired(source.isAccountNonExpired());
			prototype.setAccountNonLocked(source.isAccountNonLocked());
			prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
			prototype.setEnabled(source.isEnabled());
			if (StringUtils.isNotBlank(source.getPassword()))
				prototype.setPassword(PasswordUtils.encode(source.getPassword()));
			
			prototype.setName(StringUtils.strip(source.getName()));

			// Dynamic Field
			if (source.getDynamicField() != null) {
				String[] dynamicFields = source.getDynamicField().split(Importer.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(Importer.EQUALS)[0];
					String value = dynamicField.split(Importer.EQUALS)[1];

					boolean add = true;
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (prototype.getFields().get(i).getId().equals(prototype.getId() + Importer.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(StringUtils.strip(value));
							add = false;
						}
					}

					if (add) {
						Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
						dynamicFieldProperties.put(DynamicField.ID, dynamicFieldId);
						DynamicField entityDynamicField = modelService.findOneEntityByProperties(dynamicFieldProperties, DynamicField.class);

						if (entityDynamicField != null) {
							UserField field = modelService.create(UserField.class);
							field.setId(prototype.getId() + Importer.UNDERSCORE + dynamicFieldId);
							field.setValue(StringUtils.strip(value));
							field.setDynamicField(entityDynamicField);
							field.setUser(prototype);
							prototype.getFields().add(field);
						}
					}
				}
			}

			// User Group
			if (source.getUserGroupIds() != null) {
				String[] userGroupIds = source.getUserGroupIds().split(Importer.SPLITTER);
				for (int i = 0; i < userGroupIds.length; i++) {
					Map<String, Object> userGroupProperties = new HashMap<String, Object>();
					userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
					UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

					if (userGroup == null) {
						LOGGER.error("UserGroup not exists: " + userGroupIds[i]);
					} else {
						prototype.getUserGroups().add(userGroup);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
