package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.UserGroupCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

@Component
public class EntityCsvUserGroupConverter implements EntityConverter<UserGroupCsv, UserGroup> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvUserGroupConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup convert(UserGroupCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserGroup.ID, source.getId());

				UserGroup prototype = modelService.findOneEntityByProperties(properties, true, UserGroup.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}
			return convert(source, new UserGroup());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private UserGroup convert(UserGroupCsv source, UserGroup prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));

			// Dynamic Field
			if (StringUtils.isNotBlank(source.getDynamicField())) {
				String[] dynamicFields = source.getDynamicField().split(ImportListener.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(ImportListener.EQUALS)[0];
					String value = dynamicField.split(ImportListener.EQUALS)[1];

					boolean add = true;
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (StringUtils.equals(prototype.getFields().get(i).getId(), prototype.getId() + ImportListener.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(StringUtils.stripToNull(value));
							add = false;
						}
					}

					if (add) {
						Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
						dynamicFieldProperties.put(DynamicField.ID, dynamicFieldId);
						DynamicField entityDynamicField = modelService.findOneEntityByProperties(dynamicFieldProperties, true, DynamicField.class);

						if (entityDynamicField != null) {
							UserGroupField field = new UserGroupField();
							field.setId(prototype.getId() + ImportListener.UNDERSCORE + dynamicFieldId);
							field.setValue(StringUtils.stripToNull(value));
							field.setDynamicField(entityDynamicField);
							field.setUserGroup(prototype);
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
						UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, true, UserGroup.class);

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
