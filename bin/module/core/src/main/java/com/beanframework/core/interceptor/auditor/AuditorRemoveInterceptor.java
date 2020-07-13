package com.beanframework.core.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;

public class AuditorRemoveInterceptor extends AbstractRemoveInterceptor<Auditor> {

	@Override
	public void onRemove(Auditor model, InterceptorContext context) throws InterceptorException {
	}

}
