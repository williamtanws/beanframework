package com.beanframework.user.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightService;

@Component
public class SaveUserRightValidator implements Validator {

	@Autowired
	private UserRightService userRightService;

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
				errors.reject(UserRight.ID, localMessageService.getMessage(UserConstants.Locale.UserRight.ID_REQUIRED));
			} else {
				UserRight existsUserRight = userRightService.findById(userRight.getId());
				if (existsUserRight != null) {
					errors.reject(UserRight.ID, localMessageService.getMessage(UserConstants.Locale.UserRight.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(userRight.getId())) {
				UserRight existsUserRight = userRightService.findById(userRight.getId());
				if (existsUserRight != null) {
					if (!userRight.getUuid().equals(existsUserRight.getUuid())) {
						errors.reject(UserRight.ID, localMessageService.getMessage(UserConstants.Locale.UserRight.ID_EXISTS));
					}
				}
			}
		}
	}

}
