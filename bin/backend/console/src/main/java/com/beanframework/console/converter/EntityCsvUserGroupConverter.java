package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.UserGroupCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.service.UserGroupService;

@Component
public class EntityCsvUserGroupConverter implements EntityCsvConverter<UserGroupCsv, UserGroup> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvUserGroupConverter.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserGroupService userGroupService;

	@Override
	public UserGroup convert(UserGroupCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserGroup.ID, source.getId());

				UserGroup prototype = userGroupService.findOneEntityByProperties(properties);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(UserGroup.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private UserGroup convert(UserGroupCsv source, UserGroup prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

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
							UserGroupField field = new UserGroupField();
							field.setValue(value);
							field.setDynamicFieldSlot(entityDynamicFieldSlot);
							field.setUserGroup(prototype);
							prototype.getFields().add(field);
						}
					}
				}
			}

			// UserGroup
			if (StringUtils.isNotBlank(source.getUserGroupIds())) {
				String[] userGroupIds = source.getUserGroupIds().split(ImportListener.SPLITTER);

				boolean add = true;
				for (UserGroup userGroup : prototype.getUserGroups()) {
					for (int i = 0; i < userGroupIds.length; i++) {
						if (userGroupIds[i].equals(userGroup.getId())) {
							add = false;
						}
					}
				}

				if (add) {
					for (int i = 0; i < userGroupIds.length; i++) {
						Map<String, Object> userGroupProperties = new HashMap<String, Object>();
						userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
						UserGroup entity = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

						if (entity == null) {
							LOGGER.error("UserGroup not exists: " + userGroupIds[i]);
						} else {
							prototype.getUserGroups().add(entity);
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
