package com.beanframework.notification.event;

import com.beanframework.common.event.AbstractEvent;

public class NotificationEvent extends AbstractEvent {

	public NotificationEvent(Object source, String message) {
        super(source, message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1880790360812059230L;

}
