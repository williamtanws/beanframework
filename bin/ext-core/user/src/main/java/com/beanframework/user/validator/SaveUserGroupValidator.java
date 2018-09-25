package com.beanframework.user.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;

@Component
public class SaveUserGroupValidator implements Validator {

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserGroup.class == clazz;
	}

	@Override
	public void validate(Object target, Errors errors) {
		final UserGroup group = (UserGroup) target;

		if (group.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(group.getId())) {
				errors.reject(UserGroup.ID,
						localMessageService.getMessage(UserConstants.Locale.UserGroup.ID_REQUIRED));
			} else {
				boolean existsGroup = userGroupService.isIdExists(group.getId());
				if (existsGroup) {
					errors.reject(UserGroup.ID,
							localMessageService.getMessage(UserConstants.Locale.UserGroup.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(group.getId())) {
				UserGroup existsGroup = userGroupService.findById(group.getId());
				if (existsGroup != null) {
					if (!group.getUuid().equals(existsGroup.getUuid())) {
						errors.reject(UserGroup.ID,
								localMessageService.getMessage(UserConstants.Locale.UserGroup.ID_EXISTS));
					}
				}
			}
		}
	}

}
