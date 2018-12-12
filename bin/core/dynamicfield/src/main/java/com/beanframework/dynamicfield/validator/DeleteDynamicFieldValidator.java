package com.beanframework.dynamicfield.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.dynamicfield.DynamicFieldConstants;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.service.DynamicFieldService;

@Component
public class DeleteDynamicFieldValidator implements Validator {

	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return DynamicField.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		DynamicField userGroup = dynamicFieldService.findByUuid(uuid);
		if(userGroup == null) {
			errors.reject(DynamicField.ID, localMessageService.getMessage(DynamicFieldConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
