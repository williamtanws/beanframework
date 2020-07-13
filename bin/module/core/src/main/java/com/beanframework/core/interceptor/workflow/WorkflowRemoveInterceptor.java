package com.beanframework.core.interceptor.workflow;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.workflow.domain.Workflow;

public class WorkflowRemoveInterceptor extends AbstractRemoveInterceptor<Workflow> {

	@Override
	public void onRemove(Workflow model, InterceptorContext context) throws InterceptorException {

	}

}
