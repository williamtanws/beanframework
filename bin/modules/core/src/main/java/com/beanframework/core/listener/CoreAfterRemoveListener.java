package com.beanframework.core.listener;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.menu.domain.Menu;
import com.beanframework.workflow.domain.Workflow;

public class CoreAfterRemoveListener implements AfterRemoveListener {

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public void afterRemove(Object detachedModel, AfterRemoveEvent event) throws ListenerException {
		try {
			if (detachedModel instanceof Workflow) {
				Workflow workflow = (Workflow) detachedModel;
				repositoryService.deleteDeployment(workflow.getId());

			} else if (detachedModel instanceof Menu) {

			} else if (detachedModel instanceof Configuration) {

			}
		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}
}
