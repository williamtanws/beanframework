package com.beanframework.core.interceptor.cronjob;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.cronjob.domain.CronjobData;

public class CronjobDataPrepareInterceptor extends AbstractPrepareInterceptor<CronjobData> {

	@Override
	public void onPrepare(CronjobData model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

	}

}
