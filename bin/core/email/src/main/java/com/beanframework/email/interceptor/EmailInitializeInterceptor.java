package com.beanframework.email.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.email.domain.Email;

public class EmailInitializeInterceptor implements InitializeInterceptor<Email> {

	@Override
	public void onInitialize(Email model) throws InterceptorException {
	}

}
