package com.beanframework.core.interceptor.cronjob;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.cronjob.domain.CronjobData;

public class CronjobDataInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<CronjobData> {

	@Override
	public void onInitialDefaults(CronjobData model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
