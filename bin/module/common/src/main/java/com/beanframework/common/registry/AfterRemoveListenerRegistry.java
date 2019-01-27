package com.beanframework.common.registry;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AfterRemoveListenerRegistry {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AfterRemoveListenerRegistry.class);

	private Map<String, AfterRemoveListener> listeners = new TreeMap<String, AfterRemoveListener>();

	public Map<String, AfterRemoveListener> getListeners() {
		return listeners;
	}

	public void addListener(AfterRemoveListener listener) {
		listeners.put(listener.getClass().getName(), listener);
	}

	public void removeListener(String className) {
		listeners.remove(className);
	}

}
