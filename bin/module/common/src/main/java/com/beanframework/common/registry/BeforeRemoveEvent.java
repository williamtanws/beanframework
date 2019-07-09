package com.beanframework.common.registry;

import java.util.HashMap;
import java.util.Map;

public class BeforeRemoveEvent {

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
}
