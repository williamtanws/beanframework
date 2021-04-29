package com.beanframework.cronjob.event;

import com.beanframework.common.event.AbstractEvent;

public class CronjobEvent extends AbstractEvent {

	public CronjobEvent(Object source, String message) {
        super(source, message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1880790360812059230L;

}
