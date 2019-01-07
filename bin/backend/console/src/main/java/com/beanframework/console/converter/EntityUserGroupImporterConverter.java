package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.UserGroupCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

@Component
public class EntityUserGroupImporterConverter implements EntityConverter<UserGroupCsv, UserGroup> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityUserGroupImporterConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup convert(UserGroupCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserGroup.ID, source.getId());

				UserGroup prototype = modelService.findOneEntityByProperties(properties, UserGroup.class);

				if (prototype != null) {
					Hibernate.initialize(prototype.getUserGroups());
					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(UserGroup.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}
	}

	private UserGroup convert(UserGroupCsv source, UserGroup prototype) throws ConverterException {

		try {
			if (source.getId() != null)
				prototype.setId(source.getId());
			

			// Dynamic Field
			if (source.getDynamicField() != null) {
				String[] dynamicFields = source.getDynamicField().split(Importer.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(Importer.EQUALS)[0];
					String value = dynamicField.split(Importer.EQUALS)[1];

					boolean add = true;
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (prototype.getFields().get(i).getId().equals(prototype.getId() + Importer.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(value);
							add = false;
						}
					}

					if (add) {
						Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
						dynamicFieldProperties.put(DynamicField.ID, dynamicFieldId);
						DynamicField entityDynamicField = modelService.findOneEntityByProperties(dynamicFieldProperties, DynamicField.class);

						UserGroupField field = modelService.create(UserGroupField.class);
						field.setId(prototype.getId() + Importer.UNDERSCORE + dynamicFieldId);
						field.setValue(value);
						field.setDynamicField(entityDynamicField);
						field.setUserGroup(prototype);
						prototype.getFields().add(field);
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
