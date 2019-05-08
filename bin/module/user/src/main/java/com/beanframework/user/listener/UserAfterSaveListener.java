package com.beanframework.user.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterSaveEvent;
import com.beanframework.common.registry.AfterSaveListener;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.AuditorService;

public class UserAfterSaveListener implements AfterSaveListener {

	@Autowired
	private AuditorService auditorService;

	@Override
	public void afterSave(Object model, AfterSaveEvent event) throws ListenerException {

		try {
			if (model instanceof User) {
				User user = (User) model;

				if (AfterSaveEvent.CREATE == event.getType()) {
					auditorService.saveEntityByUser(user);

				} else if (AfterSaveEvent.UPDATE == event.getType()) {
					auditorService.saveEntityByUser(user);
				}

			}
		} catch (BusinessException e) {
			throw new ListenerException(e.getMessage(), e);
		}

	}
}
