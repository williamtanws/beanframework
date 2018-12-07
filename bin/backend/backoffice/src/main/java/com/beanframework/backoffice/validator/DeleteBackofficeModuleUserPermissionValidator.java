package com.beanframework.backoffice.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionFacade;

@Component
public class DeleteBackofficeModuleUserPermissionValidator implements Validator {

	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UUID.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		UserPermission userPermission = userPermissionFacade.findByUuid(uuid);
		if(userPermission == null) {
			errors.reject(UserPermission.ID, localMessageService.getMessage(UserConstants.Locale.UserPermission.UUID_NOT_EXISTS));
		}
	}

}
