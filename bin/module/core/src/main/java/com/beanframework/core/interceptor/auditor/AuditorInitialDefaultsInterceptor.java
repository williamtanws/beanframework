package com.beanframework.core.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;

public class AuditorInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Auditor> {

	@Override
	public void onInitialDefaults(Auditor model, InterceptorContext context) throws InterceptorException {
	}

}
