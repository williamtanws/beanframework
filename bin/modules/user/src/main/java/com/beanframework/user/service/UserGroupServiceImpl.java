package com.beanframework.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.user.UserGroupConstants;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupAttribute;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

@Service
public class UserGroupServiceImpl implements UserGroupService {
	
	@Autowired
	private ModelService modelService;
	
	@Value(UserGroupConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
	private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;
	
	@Override
	public void generateUserGroupAttribute(UserGroup model) throws Exception {
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
					for (UserGroupAttribute field : model.getAttributes()) {
						if (field.getDynamicFieldSlot().equals(dynamicFieldSlot)) {
							addField = false;
						}
					}

					if (addField) {
						UserGroupAttribute field = new UserGroupAttribute();
						field.setDynamicFieldSlot(dynamicFieldSlot);
						field.setUserGroup(model);
						model.getAttributes().add(field);
					}
				}
			}
		}
	}

	@Override
	public void generateUserAuthority(UserGroup model) throws Exception {
		Map<String, Sort.Direction> userPermissionSorts = new HashMap<String, Sort.Direction>();
		userPermissionSorts.put(UserPermission.SORT, Sort.Direction.ASC);
		List<UserPermission> userPermissions = modelService.findByPropertiesBySortByResult(null, userPermissionSorts, null, null, UserPermission.class);

		Map<String, Sort.Direction> userRightSorts = new HashMap<String, Sort.Direction>();
		userRightSorts.put(UserRight.SORT, Sort.Direction.ASC);
		List<UserRight> userRights = modelService.findByPropertiesBySortByResult(null, userRightSorts, null, null, UserRight.class);

		for (UserPermission userPermission : userPermissions) {
			for (UserRight userRight : userRights) {

				String authorityId = model.getId() + "_" + userPermission.getId() + "_" + userRight.getId();

				boolean add = true;
				if (model.getUserAuthorities() != null && model.getUserAuthorities().isEmpty() == false) {
					for (UserAuthority userAuthority : model.getUserAuthorities()) {
						if (userAuthority.getId().equals(authorityId)) {
							add = false;
						}
					}
				}

				if (add) {
					UserAuthority userAuthority = modelService.create(UserAuthority.class);
					userAuthority.setId(authorityId);
					userAuthority.setUserPermission(userPermission);
					userAuthority.setUserRight(userRight);

					model.getUserAuthorities().add(userAuthority);
				}
			}
		}
	}
}
