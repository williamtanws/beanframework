package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Workflow;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;

public class WorkflowLoadInterceptor extends AbstractLoadInterceptor<Workflow> {

	@Override
	public void onLoad(Workflow model, InterceptorContext context) throws InterceptorException {
	}

}
