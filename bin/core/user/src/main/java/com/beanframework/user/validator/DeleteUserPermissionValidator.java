package com.beanframework.user.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionService;

@Component
public class DeleteUserPermissionValidator implements Validator {

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UUID.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		UserPermission userPermission = userPermissionService.findByUuid(uuid);
		if(userPermission == null) {
			errors.reject(UserPermission.ID, localMessageService.getMessage(UserConstants.Locale.User.UUID_NOT_EXISTS));
		}
	}

}
