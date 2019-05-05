package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Workflow;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;

public class WorkflowPrepareInterceptor extends AbstractPrepareInterceptor<Workflow> {

	@Override
	public void onPrepare(Workflow model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

	}

}
