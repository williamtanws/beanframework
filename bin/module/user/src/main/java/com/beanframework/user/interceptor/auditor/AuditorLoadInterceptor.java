package com.beanframework.user.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;

public class AuditorLoadInterceptor implements LoadInterceptor<Auditor> {

	@Override
	public void onLoad(Auditor model, InterceptorContext context) throws InterceptorException {
	}
}
