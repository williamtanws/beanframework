package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Workflow;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;

public class WorkflowValidateInterceptor extends AbstractValidateInterceptor<Workflow> {

	@Override
	public void onValidate(Workflow model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
