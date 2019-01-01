package com.beanframework.email.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.email.domain.Email;

public class EmailLoadInterceptor implements LoadInterceptor<Email> {

	@Override
	public void onLoad(Email model) throws InterceptorException {
	}

}
