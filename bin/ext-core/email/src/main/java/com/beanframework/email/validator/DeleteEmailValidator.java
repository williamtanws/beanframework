package com.beanframework.email.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;
import com.beanframework.email.service.EmailService;

@Component
public class DeleteEmailValidator implements Validator {

	@Autowired
	private EmailService emailService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Email.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		Email userGroup = emailService.findByUuid(uuid);
		if(userGroup == null) {
			errors.reject(Email.ID, localMessageService.getMessage(EmailConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
