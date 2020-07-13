package com.beanframework.core.interceptor.email;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum;

public class EmailPrepareInterceptor extends AbstractPrepareInterceptor<Email> {

	@Override
	public void onPrepare(Email model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
		if (model.getStatus() == null) {
			model.setStatus(EmailEnum.Status.DRAFT);
		}
	}
}
