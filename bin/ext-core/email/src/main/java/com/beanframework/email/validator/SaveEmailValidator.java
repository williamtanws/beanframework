package com.beanframework.email.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.email.domain.Email;

@Component
public class SaveEmailValidator implements Validator {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	@Override
	public boolean supports(Class<?> clazz) {
		return Email.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		Email email = (Email) target;

		if (StringUtils.isEmpty(email.getToRecipients()) && StringUtils.isEmpty(email.getCcRecipients())
				&& StringUtils.isEmpty(email.getBccRecipients())) {
			errors.reject("recipient", "Please input recipient");
		} else {
			if (StringUtils.isNotEmpty(email.getToRecipients())) {
				valiateRecipients(email.getToRecipients(), errors);
			}
			if (StringUtils.isNotEmpty(email.getCcRecipients())) {
				valiateRecipients(email.getCcRecipients(), errors);
			}
			if (StringUtils.isNotEmpty(email.getBccRecipients())) {
				valiateRecipients(email.getBccRecipients(), errors);
			}
		}

	}

	private void valiateRecipients(final String recipients, final Errors errors) {
		String[] recipientsArray = recipients.split(";");
		for (int i = 0; i < recipientsArray.length; i++) {
			if (validateEmail(recipientsArray[i]) == false) {
				errors.reject("Recipient", "Wrong email format");
			}
		}
	}

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

}
