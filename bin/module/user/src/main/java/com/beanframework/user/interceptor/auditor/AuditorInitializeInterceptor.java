package com.beanframework.user.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;

public class AuditorInitializeInterceptor implements InitializeInterceptor<Auditor> {

	@Override
	public void onInitialize(Auditor model, InterceptorContext context) throws InterceptorException {
	}
}
