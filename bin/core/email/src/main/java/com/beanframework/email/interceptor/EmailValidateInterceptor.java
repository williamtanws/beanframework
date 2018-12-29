package com.beanframework.email.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.email.domain.Email;

public class EmailValidateInterceptor implements ValidateInterceptor<Email> {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	@Override
	public void onValidate(Email model) throws InterceptorException {
		
		if (StringUtils.isBlank(model.getToRecipients()) && StringUtils.isBlank(model.getCcRecipients())
				&& StringUtils.isBlank(model.getBccRecipients())) {
			throw new InterceptorException("Please input recipient");
		} else {
			if (StringUtils.isNotBlank(model.getToRecipients())) {
				valiateRecipients(model.getToRecipients());
			}
			if (StringUtils.isNotBlank(model.getCcRecipients())) {
				valiateRecipients(model.getCcRecipients());
			}
			if (StringUtils.isNotBlank(model.getBccRecipients())) {
				valiateRecipients(model.getBccRecipients());
			}
		}
	}
	
	private void valiateRecipients(final String recipients) throws InterceptorException {
		String[] recipientsArray = recipients.split(";");
		for (int i = 0; i < recipientsArray.length; i++) {
			if (validateEmail(recipientsArray[i]) == false) {
				throw new InterceptorException("Wrong email format");
			}
		}
	}

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

}
