package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.EmployeeCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;

@Component
public class EntityCsvEmployeeConverter implements EntityCsvConverter<EmployeeCsv, Employee> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvEmployeeConverter.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Employee convert(EmployeeCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Employee.ID, source.getId());

				Employee prototype = employeeService.findOneEntityByProperties(properties);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Employee.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Employee convertToEntity(EmployeeCsv source, Employee prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

			if (source.getAccountNonExpired() != null)
				prototype.setAccountNonExpired(source.getAccountNonExpired());

			if (source.getAccountNonLocked() != null)
				prototype.setAccountNonLocked(source.getAccountNonLocked());

			if (source.getCredentialsNonExpired() != null)
				prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());

			if (source.getEnabled() != null)
				prototype.setEnabled(source.getEnabled());

			if (StringUtils.isNotBlank(source.getPassword()))
				prototype.setPassword(passwordEncoder.encode(source.getPassword()));

			// Dynamic Field Slot
			if (StringUtils.isNotBlank(source.getDynamicFieldSlotIds())) {
				String[] dynamicFieldSlots = source.getDynamicFieldSlotIds().split(ImportListener.SPLITTER);
				for (String dynamicFieldSlot : dynamicFieldSlots) {
					String dynamicFieldSlotId = StringUtils.stripToNull(dynamicFieldSlot.split(ImportListener.EQUALS)[0]);
					String value = StringUtils.stripToNull(dynamicFieldSlot.split(ImportListener.EQUALS)[1]);

					boolean add = true;
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (StringUtils.equals(prototype.getFields().get(i).getDynamicFieldSlot().getId(), dynamicFieldSlotId)) {
							prototype.getFields().get(i).setValue(StringUtils.stripToNull(value));
							add = false;
						}
					}

					if (add) {
						Map<String, Object> dynamicFieldSlotProperties = new HashMap<String, Object>();
						dynamicFieldSlotProperties.put(DynamicField.ID, dynamicFieldSlotId);
						DynamicFieldSlot entityDynamicFieldSlot = modelService.findOneEntityByProperties(dynamicFieldSlotProperties, DynamicFieldSlot.class);

						if (entityDynamicFieldSlot != null) {
							UserField field = new UserField();
							field.setValue(value);
							field.setDynamicFieldSlot(entityDynamicFieldSlot);
							field.setUser(prototype);
							prototype.getFields().add(field);
						}
					}
				}
			}

			// User Group
			if (StringUtils.isNotBlank(source.getUserGroupIds())) {
				String[] userGroupIds = source.getUserGroupIds().split(ImportListener.SPLITTER);
				for (int i = 0; i < userGroupIds.length; i++) {
					boolean add = true;
					for (UserGroup userGroup : prototype.getUserGroups()) {
						if (StringUtils.equals(userGroup.getId(), userGroupIds[i]))
							add = false;
					}

					if (add) {
						Map<String, Object> userGroupProperties = new HashMap<String, Object>();
						userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
						UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

						if (userGroup == null) {
							LOGGER.error("UserGroup ID not exists: " + userGroupIds[i]);
						} else {
							prototype.getUserGroups().add(userGroup);
						}
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
