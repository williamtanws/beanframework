package com.beanframework.cronjob.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobLoadInterceptor implements LoadInterceptor<Cronjob> {

	@Override
	public void onLoad(Cronjob model) throws InterceptorException {
	}

}
