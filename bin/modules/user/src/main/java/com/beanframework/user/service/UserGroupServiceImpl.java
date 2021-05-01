package com.beanframework.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.user.UserGroupConstants;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

@Service
public class UserGroupServiceImpl implements UserGroupService {
	
	@Autowired
	private ModelService modelService;
	
	@Value(UserGroupConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
	private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

	@Override
	public void generateUserGroupFieldsOnInitialDefault(UserGroup model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
		Configuration configuration = modelService.findOneByProperties(properties, Configuration.class);

		if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
			properties = new HashMap<String, Object>();
			properties.put(DynamicFieldTemplate.ID, configuration.getValue());

			DynamicFieldTemplate dynamicFieldTemplate = modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

			if (dynamicFieldTemplate != null) {

				for (UUID dynamicFieldSlot : dynamicFieldTemplate.getDynamicFieldSlots()) {
					UserGroupField field = new UserGroupField();
					field.setDynamicFieldSlot(dynamicFieldSlot);
					field.setUserGroup(model);
					model.getFields().add(field);
				}
			}
		}
	}
	
	@Override
	public void generateUserGroupFieldOnLoad(UserGroup model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
		Configuration configuration = modelService.findOneByProperties(properties, Configuration.class);

		if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
			properties = new HashMap<String, Object>();
			properties.put(DynamicFieldTemplate.ID, configuration.getValue());

			DynamicFieldTemplate dynamicFieldTemplate = modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

			if (dynamicFieldTemplate != null) {

				for (UUID dynamicFieldSlot : dynamicFieldTemplate.getDynamicFieldSlots()) {

					boolean addField = true;
					for (UserGroupField field : model.getFields()) {
						if (field.getDynamicFieldSlot().equals(dynamicFieldSlot)) {
							addField = false;
						}
					}

					if (addField) {
						UserGroupField field = new UserGroupField();
						field.setDynamicFieldSlot(dynamicFieldSlot);
						field.setUserGroup(model);
						model.getFields().add(field);
					}
				}
			}
		}
	}
}
