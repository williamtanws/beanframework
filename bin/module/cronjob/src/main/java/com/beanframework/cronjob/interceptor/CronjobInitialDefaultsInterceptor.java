package com.beanframework.cronjob.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Cronjob> {

	@Override
	public void onInitialDefaults(Cronjob model, InterceptorContext context) throws InterceptorException {
	}

}
