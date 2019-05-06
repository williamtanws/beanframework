package com.beanframework.workflow.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.workflow.domain.Workflow;

public class WorkflowLoadInterceptor extends AbstractLoadInterceptor<Workflow> {

	@Override
	public void onLoad(Workflow model, InterceptorContext context) throws InterceptorException {
	}

}
