package com.beanframework.email.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum;

public class EmailPrepareInterceptor implements PrepareInterceptor<Email> {

	@Override
	public void onPrepare(Email model, InterceptorContext context) throws InterceptorException {
		if (model.getStatus() == null) {
			model.setStatus(EmailEnum.Status.DRAFT);
		}
	}
}
