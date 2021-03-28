package com.beanframework.common.registry;

import java.util.HashMap;
import java.util.Map;

public class AfterRemoveEvent {

	private Map<String, Object> dataMapFromBeforeRemoveEvent = new HashMap<String, Object>();

	public AfterRemoveEvent(Map<String, Object> dataMapFromBeforeRemoveEvent) {
		super();
		this.dataMapFromBeforeRemoveEvent = dataMapFromBeforeRemoveEvent;
	}

	public Map<String, Object> getDataMapFromBeforeRemoveEvent() {
		return dataMapFromBeforeRemoveEvent;
	}

	public void setDataMapFromBeforeRemoveEvent(Map<String, Object> dataMapFromBeforeRemoveEvent) {
		this.dataMapFromBeforeRemoveEvent = dataMapFromBeforeRemoveEvent;
	}
}
