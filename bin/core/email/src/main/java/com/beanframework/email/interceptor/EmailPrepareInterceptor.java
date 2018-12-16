package com.beanframework.email.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.email.domain.Email;

public class EmailPrepareInterceptor implements PrepareInterceptor<Email> {

	@Override
	public void onPrepare(Email model) throws InterceptorException {
		
	}

}
