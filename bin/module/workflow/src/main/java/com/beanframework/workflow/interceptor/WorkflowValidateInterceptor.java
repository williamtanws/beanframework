package com.beanframework.workflow.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.workflow.domain.Workflow;

public class WorkflowValidateInterceptor extends AbstractValidateInterceptor<Workflow> {

	@Override
	public void onValidate(Workflow model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
