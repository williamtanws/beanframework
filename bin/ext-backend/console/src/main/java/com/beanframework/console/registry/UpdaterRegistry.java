package com.beanframework.console.registry;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.domain.Updater;

@Component
public class UpdaterRegistry {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Updater> updaters = new TreeMap<String, Updater>();

	public Map<String, Updater> getUpdaters() {
		return updaters;
	}

	public void addUpdater(Updater updater) {

		if (StringUtils.isEmpty(updater.getKey())) {
			throw new RuntimeException("Updater Key is missing");
		}
		if (StringUtils.isEmpty(updater.getName())) {
			throw new RuntimeException("Updater Name is missing");
		}
		if (StringUtils.isEmpty(updater.getDescription())) {
			throw new RuntimeException("Updater Description is missing");
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Added '"+updater.getKey()+"' "+updater.getName());
		}
	
		updaters.put(updater.getKey(), updater);
	}

	public void removeUpdater(String key) {
		updaters.remove(key);
	}
}
