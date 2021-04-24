package com.beanframework.common.registry;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BeforeRemoveListenerRegistry {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BeforeRemoveListenerRegistry.class);

	private Map<String, BeforeRemoveListener> listeners = new TreeMap<String, BeforeRemoveListener>();

	public Map<String, BeforeRemoveListener> getListeners() {
		return listeners;
	}

	public void addListener(BeforeRemoveListener listener) {
		listeners.put(listener.getClass().getName(), listener);
	}

	public void removeListener(String className) {
		listeners.remove(className);
	}

}
