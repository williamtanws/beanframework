package com.beanframework.core.interceptor.cronjob;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobLoadInterceptor extends AbstractLoadInterceptor<Cronjob> {

	@Override
	public void onLoad(Cronjob model, InterceptorContext context) throws InterceptorException {
	}

}
