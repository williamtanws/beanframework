package com.beanframework.cronjob.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobRemoveInterceptor extends AbstractRemoveInterceptor<Cronjob> {

	@Override
	public void onRemove(Cronjob model, InterceptorContext context) throws InterceptorException {
		super.onRemove(model, context);
	}

}
