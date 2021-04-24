package com.beanframework.common.registry;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BeforeSaveListenerRegistry {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BeforeSaveListenerRegistry.class);

	private Map<String, BeforeSaveListener> listeners = new TreeMap<String, BeforeSaveListener>();

	public Map<String, BeforeSaveListener> getListeners() {
		return listeners;
	}

	public void addListener(BeforeSaveListener listener) {
		listeners.put(listener.getClass().getName(), listener);
	}

	public void SaveListener(String className) {
		listeners.remove(className);
	}

}
