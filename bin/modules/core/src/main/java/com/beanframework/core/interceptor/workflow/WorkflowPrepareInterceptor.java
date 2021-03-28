package com.beanframework.core.interceptor.workflow;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.workflow.domain.Workflow;

public class WorkflowPrepareInterceptor extends AbstractPrepareInterceptor<Workflow> {

	@Override
	public void onPrepare(Workflow model, InterceptorContext context) throws InterceptorException {

	}

}
