package com.beanframework.email.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum;

public class EmailInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Email> {

	@Override
	public void onInitialDefaults(Email model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
		model.setStatus(EmailEnum.Status.DRAFT);
	}

}
