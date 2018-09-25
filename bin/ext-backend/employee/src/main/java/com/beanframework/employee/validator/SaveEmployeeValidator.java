package com.beanframework.employee.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;

@Component
public class SaveEmployeeValidator implements Validator {

	@Autowired
	private EmployeeService adminService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final Employee admin = (Employee) target;

		if (admin.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(admin.getId())) {
				errors.reject(Employee.ID, localMessageService.getMessage(EmployeeConstants.Locale.ID_REQUIRED));
			} else if (StringUtils.isEmpty(admin.getPassword())) {
				errors.reject(Employee.PASSWORD, localMessageService.getMessage(EmployeeConstants.Locale.PASSWORD_REQUIRED));
			} else {
				Employee existsEmployee = adminService.findById(admin.getId());
				if (existsEmployee != null) {
					errors.reject(Employee.ID, localMessageService.getMessage(EmployeeConstants.Locale.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(admin.getId())) {
				Employee existsEmployee = adminService.findById(admin.getId());
				if (existsEmployee != null) {
					if (!admin.getUuid().equals(existsEmployee.getUuid())) {
						errors.reject(Employee.ID, localMessageService.getMessage(EmployeeConstants.Locale.ID_EXISTS));
					}
				}
			}
		}
	}

}
