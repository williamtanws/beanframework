package com.beanframework.workflow.interceptor;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.workflow.domain.Workflow;

public class WorkflowRemoveInterceptor extends AbstractRemoveInterceptor<Workflow> {
	
	@Autowired
	private RepositoryService repositoryService;

	@Override
	public void onRemove(Workflow model, InterceptorContext context) throws InterceptorException {
		repositoryService.deleteDeployment(model.getId());
	}

}
