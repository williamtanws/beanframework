package com.beanframework.workflow.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
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
			if (StringUtils.isBlank(model.getDeploymentId()) && StringUtils.isNotBlank(model.getClasspath())) {
				Deployment deployment = repositoryService.createDeployment().addClasspathResource(model.getClasspath()).deploy();
				model.setDeploymentId(deployment.getId());
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
