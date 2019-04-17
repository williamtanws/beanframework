package com.beanframework.cronjob.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.cronjob.domain.CronjobData;

public class CronjobDataRemoveInterceptor extends AbstractRemoveInterceptor<CronjobData> {

	@Override
	public void onRemove(CronjobData model, InterceptorContext context) throws InterceptorException {
		super.onRemove(model, context);
	}

}
