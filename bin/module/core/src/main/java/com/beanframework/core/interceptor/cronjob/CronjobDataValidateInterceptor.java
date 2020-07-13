package com.beanframework.core.interceptor.cronjob;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.cronjob.domain.CronjobData;

public class CronjobDataValidateInterceptor extends AbstractValidateInterceptor<CronjobData> {

	@Override
	public void onValidate(CronjobData model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
