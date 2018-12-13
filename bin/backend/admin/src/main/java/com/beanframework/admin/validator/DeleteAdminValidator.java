package com.beanframework.admin.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminFacade;
import com.beanframework.common.service.LocaleMessageService;

@Component
public class DeleteAdminValidator implements Validator {

	@Autowired
	private AdminFacade adminFacade;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Admin.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		Admin admin = adminFacade.findByUuid(uuid);
		if(admin == null) {
			errors.reject(Admin.ID, localMessageService.getMessage(AdminConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
