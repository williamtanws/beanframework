package com.beanframework.admin.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminService;
import com.beanframework.common.service.LocaleMessageService;

@Component
public class DeleteAdminValidator implements Validator {

	@Autowired
	private AdminService adminService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Admin.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		Admin admin = adminService.findByUuid(uuid);
		if(admin == null) {
			errors.reject(Admin.ID, localMessageService.getMessage(AdminConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
