package com.beanframework.core.listener;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.workflow.domain.Workflow;

public class CoreAfterRemoveListener implements AfterRemoveListener {

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public void afterRemove(Object model, AfterRemoveEvent event) throws ListenerException {
		if (model instanceof Workflow) {
			Workflow workflow = (Workflow) model;
			repositoryService.deleteDeployment(workflow.getId());
		}
	}

}
