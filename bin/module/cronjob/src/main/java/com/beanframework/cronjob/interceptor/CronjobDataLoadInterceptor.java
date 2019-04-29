package com.beanframework.cronjob.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.cronjob.domain.CronjobData;

public class CronjobDataLoadInterceptor extends AbstractLoadInterceptor<CronjobData> {

	@Override
	public void onLoad(CronjobData model, InterceptorContext context) throws InterceptorException {
	}

}
