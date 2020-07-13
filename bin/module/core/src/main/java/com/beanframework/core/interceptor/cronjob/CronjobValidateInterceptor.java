package com.beanframework.core.interceptor.cronjob;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobValidateInterceptor extends AbstractValidateInterceptor<Cronjob> {

	@Override
	public void onValidate(Cronjob model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
