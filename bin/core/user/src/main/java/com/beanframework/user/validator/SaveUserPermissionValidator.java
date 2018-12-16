package com.beanframework.user.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.UserPermissionConstants;
import com.beanframework.user.domain.UserPermission;

@Component
public class SaveUserPermissionValidator implements Validator {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserPermission.class == clazz;
	}

	@Override
	public void validate(Object target, Errors errors) {
		final UserPermission userPermission = (UserPermission) target;

		if (userPermission.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(userPermission.getId())) {
				errors.reject(UserPermission.ID,
						localMessageService.getMessage(UserPermissionConstants.Locale.ID_REQUIRED));
			} else {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermission.ID, userPermission.getId());
				
				boolean existsPermission = modelService.existsByProperties(properties, UserPermission.class);
				
				if (existsPermission) {
					errors.reject(UserPermission.ID,
							localMessageService.getMessage(UserPermissionConstants.Locale.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(userPermission.getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermission.ID, userPermission.getId());
				
				UserPermission existsPermission = modelService.findOneEntityByProperties(properties, UserPermission.class);
				
				if (existsPermission != null) {
					if (!userPermission.getUuid().equals(existsPermission.getUuid())) {
						errors.reject(UserPermission.ID,
								localMessageService.getMessage(UserPermissionConstants.Locale.ID_EXISTS));
					}
				}
			}
		}
	}

}
