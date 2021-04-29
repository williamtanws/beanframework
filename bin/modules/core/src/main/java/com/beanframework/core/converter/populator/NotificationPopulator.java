package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.notification.domain.Notification;


public class NotificationPopulator extends AbstractPopulator<Notification, NotificationDto> implements Populator<Notification, NotificationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(NotificationPopulator.class);

	@Override
	public void populate(Notification source, NotificationDto target) throws PopulatorException {
		populateGeneric(source, target);
		target.setIcon(source.getIcon());
		target.setType(source.getType());
		target.setMessage(source.getMessage());
		target.setParameters(source.getParameters());
	}

}
