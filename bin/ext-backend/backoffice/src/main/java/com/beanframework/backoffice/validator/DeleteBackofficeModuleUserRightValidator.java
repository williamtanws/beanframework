package com.beanframework.backoffice.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightFacade;

@Component
public class DeleteBackofficeModuleUserRightValidator implements Validator {

	@Autowired
	private UserRightFacade userRightFacade;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UUID.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		UserRight userRight = userRightFacade.findByUuid(uuid);
		if(userRight == null) {
			errors.reject(UserRight.ID, localMessageService.getMessage(UserConstants.Locale.UserRight.UUID_NOT_EXISTS));
		}
	}

}
