package com.beanframework.core.interceptor.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.email.domain.Email;

public class EmailValidateInterceptor extends AbstractValidateInterceptor<Email> {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public void onValidate(Email model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

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

	private void valiateRecipients(final String recipients) throws InterceptorException {
		String[] recipientsArray = recipients.split(";");
		for (int i = 0; i < recipientsArray.length; i++) {
			if (validateEmail(recipientsArray[i]) == Boolean.FALSE) {
				throw new InterceptorException("Wrong email format");
			}
		}
	}

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

}
