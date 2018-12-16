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
import com.beanframework.user.UserRightConstants;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

@Component
public class SaveUserRightValidator implements Validator {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRight.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final UserRight userRight = (UserRight) target;

		if (userRight.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(userRight.getId())) {
				errors.reject(UserRight.ID, localMessageService.getMessage(UserRightConstants.Locale.ID_REQUIRED));
			} else {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermission.ID, userRight.getId());
				
				boolean existsUserRight = modelService.existsByProperties(properties, UserRight.class);
				
				if (existsUserRight) {
					errors.reject(UserRight.ID, localMessageService.getMessage(UserRightConstants.Locale.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(userRight.getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.ID, userRight.getId());
				
				UserRight existsUserRight = modelService.findOneEntityByProperties(properties, UserRight.class);
								
				if (existsUserRight != null) {
					if (!userRight.getUuid().equals(existsUserRight.getUuid())) {
						errors.reject(UserRight.ID, localMessageService.getMessage(UserRightConstants.Locale.ID_EXISTS));
					}
				}
			}
		}
	}

}
