package com.beanframework.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.beanframework.common.event.AbstractEvent;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.core.facade.NotificationFacade;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.event.CronjobEvent;

@Component
public class CoreNotificationEventListener implements ApplicationListener<AbstractEvent> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CoreNotificationEventListener.class);

	@Autowired
	private NotificationFacade notificationFacade;

	@Override
	public void onApplicationEvent(AbstractEvent event) {

		try {
			if (event instanceof CronjobEvent) {
				NotificationDto dto = notificationFacade.createDto();
				dto.setMessage(event.getMessage());
				dto.setType(Cronjob.class.getSimpleName());
				notificationFacade.create(dto);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}