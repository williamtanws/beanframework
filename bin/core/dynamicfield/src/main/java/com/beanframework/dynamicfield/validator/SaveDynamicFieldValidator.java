package com.beanframework.dynamicfield.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class SaveDynamicFieldValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return DynamicField.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {

	}

}
