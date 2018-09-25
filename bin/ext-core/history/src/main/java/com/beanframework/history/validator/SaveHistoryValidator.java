package com.beanframework.history.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.history.domain.History;

@Component
public class SaveHistoryValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return History.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {

	}

}
