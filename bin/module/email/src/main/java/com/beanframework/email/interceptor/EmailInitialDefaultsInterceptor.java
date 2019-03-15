package com.beanframework.email.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum;

public class EmailInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Email> {

	@Override
	public void onInitialDefaults(Email model, InterceptorContext context) throws InterceptorException {
		model.setStatus(EmailEnum.Status.DRAFT);
	}

}
