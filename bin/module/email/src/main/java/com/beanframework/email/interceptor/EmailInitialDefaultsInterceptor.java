package com.beanframework.email.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum;

public class EmailInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Email> {

	@Override
	public void onInitialDefaults(Email model) throws InterceptorException {
		model.setStatus(EmailEnum.Status.DRAFT);
	}

}
