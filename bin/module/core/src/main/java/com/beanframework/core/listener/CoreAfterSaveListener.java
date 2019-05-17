package com.beanframework.core.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterSaveEvent;
import com.beanframework.common.registry.AfterSaveListener;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.AuditorService;

public class CoreAfterSaveListener implements AfterSaveListener {

	@Autowired
	private AuditorService auditorService;

	@Override
	public void afterSave(final Object model, final AfterSaveEvent event) throws ListenerException {

		try {
			if (model instanceof User) {
				User user = (User) model;

				auditorService.saveEntityByUser(user);
			}
		} catch (BusinessException e) {
			throw new ListenerException(e.getMessage(), e);
		}

	}
}
