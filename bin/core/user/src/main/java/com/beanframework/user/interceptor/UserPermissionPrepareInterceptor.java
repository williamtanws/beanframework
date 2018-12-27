package com.beanframework.user.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class UserPermissionPrepareInterceptor implements PrepareInterceptor<UserPermission> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public void onPrepare(UserPermission model) throws InterceptorException {
		generateUserPermissionField(model);
		
		for (int i = 0; i < model.getUserPermissionFields().size(); i++) {
			if (StringUtils.isBlank(model.getUserPermissionFields().get(i).getValue())) {
				model.getUserPermissionFields().get(i).setValue(null);
			}
		}
	}
	
	private void generateUserPermissionField(UserPermission model) throws InterceptorException {
		try {
			Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
			dynamicFieldProperties.put(DynamicField.FIELD_GROUP, UserPermission.class.getSimpleName());
			List<DynamicField> dynamicFields = modelService.findEntityByProperties(dynamicFieldProperties, DynamicField.class);

			for (DynamicField dynamicField : dynamicFields) {

				boolean add = true;
				for (UserPermissionField modelUserGroupField : model.getUserPermissionFields()) {
					if (dynamicField.getUuid().equals(modelUserGroupField.getDynamicField().getUuid())) {
						add = false;
					}
				}

				if (add) {
					UserPermissionField userGroupField = modelService.create(UserPermissionField.class);
					userGroupField.setDynamicField(dynamicField);
					userGroupField.setId(model.getId() + "_" + dynamicField.getId());

					userGroupField.setUserPermission(model);
					model.getUserPermissionFields().add(userGroupField);
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
