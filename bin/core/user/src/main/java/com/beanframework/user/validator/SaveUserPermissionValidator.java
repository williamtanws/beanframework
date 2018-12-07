package com.beanframework.user.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionService;

@Component
public class SaveUserPermissionValidator implements Validator {

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserPermission.class == clazz;
	}

	@Override
	public void validate(Object target, Errors errors) {
		final UserPermission group = (UserPermission) target;

		if (group.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(group.getId())) {
				errors.reject(UserPermission.ID,
						localMessageService.getMessage(UserConstants.Locale.UserPermission.ID_REQUIRED));
			} else {
				boolean existsPermission = userPermissionService.isIdExists(group.getId());
				if (existsPermission) {
					errors.reject(UserPermission.ID,
							localMessageService.getMessage(UserConstants.Locale.UserPermission.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(group.getId())) {
				UserPermission existsPermission = userPermissionService.findById(group.getId());
				if (existsPermission != null) {
					if (!group.getUuid().equals(existsPermission.getUuid())) {
						errors.reject(UserPermission.ID,
								localMessageService.getMessage(UserConstants.Locale.UserPermission.ID_EXISTS));
					}
				}
			}
		}
	}

}
