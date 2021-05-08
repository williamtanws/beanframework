package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.notification.domain.Notification;

public class NotificationDtoConverter extends AbstractDtoConverter<Notification, NotificationDto> implements DtoConverter<Notification, NotificationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(NotificationDtoConverter.class);

	@Override
	public NotificationDto convert(Notification source) throws ConverterException {
		return super.convert(source, new NotificationDto());
	}
}
