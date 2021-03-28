package com.beanframework.imex.registry;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImportListenerRegistry {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ImportListenerRegistry.class);

	private Map<String, ImportListener> listeners = new TreeMap<String, ImportListener>();

	public Map<String, ImportListener> getListeners() {
		return listeners;
	}

	public void addListener(ImportListener listener) {

		if (StringUtils.isBlank(listener.getType())) {
			throw new RuntimeException("Listener Key is missing");
		}
		if (StringUtils.isBlank(listener.getName())) {
			throw new RuntimeException("Listener Name is missing");
		}
		if (StringUtils.isBlank(listener.getDescription())) {
			throw new RuntimeException("Listener Description is missing");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Added '" + listener.getType() + "' " + listener.getName());
		}

		listeners.put(listener.getType(), listener);
	}

	public void removeListener(String key) {
		listeners.remove(key);
	}
}
