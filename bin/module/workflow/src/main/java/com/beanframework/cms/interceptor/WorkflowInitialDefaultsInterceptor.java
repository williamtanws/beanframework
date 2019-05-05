package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Workflow;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;

public class WorkflowInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Workflow> {

	@Override
	public void onInitialDefaults(Workflow model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
