package com.beanframework.cronjob.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobPrepareInterceptor implements PrepareInterceptor<Cronjob> {

	@Override
	public void onPrepare(Cronjob model, InterceptorContext context) throws InterceptorException {

	}

}
