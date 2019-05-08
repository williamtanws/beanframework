package com.beanframework.workflow.interceptor;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.workflow.domain.Workflow;

public class WorkflowPrepareInterceptor extends AbstractPrepareInterceptor<Workflow> {

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public void onPrepare(Workflow model, InterceptorContext context) throws InterceptorException {
		try {
			Deployment deployment = repositoryService.createDeployment().addClasspathResource(model.getClasspath()).deploy();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

			model.setId(processDefinition.getId());
			model.setName(processDefinition.getName());

		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
