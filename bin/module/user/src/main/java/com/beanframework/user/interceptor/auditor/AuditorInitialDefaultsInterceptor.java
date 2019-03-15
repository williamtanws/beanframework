package com.beanframework.user.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;

public class AuditorInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Auditor> {

	@Override
	public void onInitialDefaults(Auditor model, InterceptorContext context) throws InterceptorException {
	}

}
