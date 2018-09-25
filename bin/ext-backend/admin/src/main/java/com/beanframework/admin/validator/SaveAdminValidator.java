package com.beanframework.admin.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminService;
import com.beanframework.common.service.LocaleMessageService;

@Component
public class SaveAdminValidator implements Validator {

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
		final Admin admin = (Admin) target;

		if (admin.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(admin.getId())) {
				errors.reject(Admin.ID, localMessageService.getMessage(AdminConstants.Locale.ID_REQUIRED));
			} else if (StringUtils.isEmpty(admin.getPassword())) {
				errors.reject(Admin.PASSWORD, localMessageService.getMessage(AdminConstants.Locale.PASSWORD_REQUIRED));
			} else {
				Admin existsAdmin = adminService.findById(admin.getId());
				if (existsAdmin != null) {
					errors.reject(Admin.ID, localMessageService.getMessage(AdminConstants.Locale.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(admin.getId())) {
				Admin existsAdmin = adminService.findById(admin.getId());
				if (existsAdmin != null) {
					if (!admin.getUuid().equals(existsAdmin.getUuid())) {
						errors.reject(Admin.ID, localMessageService.getMessage(AdminConstants.Locale.ID_EXISTS));
					}
				}
			}
		}
	}

}
