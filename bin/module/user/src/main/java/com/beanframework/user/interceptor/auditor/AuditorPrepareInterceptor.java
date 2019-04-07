package com.beanframework.user.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;

public class AuditorPrepareInterceptor extends AbstractPrepareInterceptor<Auditor> {

	@Override
	public void onPrepare(Auditor model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}
}
