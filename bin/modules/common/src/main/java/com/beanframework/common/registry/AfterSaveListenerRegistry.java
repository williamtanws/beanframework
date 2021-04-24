package com.beanframework.common.registry;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AfterSaveListenerRegistry {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AfterSaveListenerRegistry.class);

	private Map<String, AfterSaveListener> listeners = new TreeMap<String, AfterSaveListener>();

	public Map<String, AfterSaveListener> getListeners() {
		return listeners;
	}

	public void addListener(AfterSaveListener listener) {
		listeners.put(listener.getClass().getName(), listener);
	}

	public void removeListener(String className) {
		listeners.remove(className);
	}

}
