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
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class UserRightPrepareInterceptor implements PrepareInterceptor<UserRight> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public void onPrepare(UserRight model) throws InterceptorException {
		generateUserRightField(model);
		
		for (int i = 0; i < model.getUserRightFields().size(); i++) {
			if (StringUtils.isBlank(model.getUserRightFields().get(i).getValue())) {
				model.getUserRightFields().get(i).setValue(null);
			}
		}
	}
	
	private void generateUserRightField(UserRight model) throws InterceptorException {
		try {
			Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
			dynamicFieldProperties.put(DynamicField.FIELD_GROUP, UserRight.class.getSimpleName());
			List<DynamicField> dynamicFields = modelService.findEntityByProperties(dynamicFieldProperties, DynamicField.class);

			for (DynamicField dynamicField : dynamicFields) {

				boolean add = true;
				for (UserRightField modelUserGroupField : model.getUserRightFields()) {
					if (dynamicField.getUuid().equals(modelUserGroupField.getDynamicField().getUuid())) {
						add = false;
					}
				}

				if (add) {
					UserRightField userRightField = modelService.create(UserRightField.class);
					userRightField.setDynamicField(dynamicField);
					userRightField.setId(model.getId() + "_" + dynamicField.getId());

					userRightField.setUserRight(model);
					model.getUserRightFields().add(userRightField);
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
