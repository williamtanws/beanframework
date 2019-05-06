package com.beanframework.workflow.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.workflow.domain.Workflow;

public class WorkflowInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Workflow> {

	@Override
	public void onInitialDefaults(Workflow model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
