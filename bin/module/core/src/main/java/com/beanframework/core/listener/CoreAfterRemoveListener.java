package com.beanframework.core.listener;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.menu.service.MenuService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.workflow.domain.Workflow;

public class CoreAfterRemoveListener implements AfterRemoveListener {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private CacheManager cacheManager;

	@Override
	public void afterRemove(Object model, AfterRemoveEvent event) throws ListenerException {
		if (model instanceof Workflow) {
			Workflow workflow = (Workflow) model;
			repositoryService.deleteDeployment(workflow.getId());
		} else if (model instanceof UserGroup) {
			cacheManager.getCache(MenuService.CACHE_MENU_TREE).clear();
		} else if (model instanceof Configuration) {
			cacheManager.getCache(ConfigurationService.CACHE_CONFIGURATION).clear();
		}
	}

}
