package com.beanframework.employee.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.domain.Employee;

@Component
public class SaveEmployeeProfileValidator implements Validator {

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		if(target instanceof Employee) {
			final Employee employee = (Employee) target;

			if (StringUtils.isEmpty(employee.getName())) {
				errors.reject(Employee.NAME, localMessageService.getMessage(EmployeeConstants.Locale.NAME_REQUIRED));
			}
		}
		
		if(target instanceof MultipartFile) {
			final MultipartFile picture = (MultipartFile) target;
			
			if (picture != null && picture.isEmpty() == false) {
				String mimetype = picture.getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == false) {
					errors.reject(Employee.PICTURE, localMessageService.getMessage(EmployeeConstants.Locale.PICTURE_WRONGFORMAT));
				}
			}
		}
	}
}
