package com.beanframework.employee.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;

@Component
public class DeleteEmployeeValidator implements Validator {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {

		if (target instanceof UUID) {
			UUID uuid = (UUID) target;

			Employee employee = employeeService.findByUuid(uuid);
			if (employee == null) {
				errors.reject(Employee.UUID, localMessageService.getMessage(EmployeeConstants.Locale.UUID_NOT_EXISTS));
			}
		}
		
		if (target instanceof String) {
			String id = (String) target;

			Employee employee = employeeService.findById(id);
			if (employee == null) {
				errors.reject(Employee.ID, localMessageService.getMessage(EmployeeConstants.Locale.ID_NOT_EXISTS));
			}
		}
	}

}
