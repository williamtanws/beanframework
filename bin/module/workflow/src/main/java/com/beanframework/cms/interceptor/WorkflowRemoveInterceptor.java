package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Workflow;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;

public class WorkflowRemoveInterceptor extends AbstractRemoveInterceptor<Workflow> {

	@Override
	public void onRemove(Workflow model, InterceptorContext context) throws InterceptorException {
		super.onRemove(model, context);
	}

}
