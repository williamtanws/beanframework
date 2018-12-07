package com.beanframework.console.registry;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.domain.Remover;

@Component
public class RemoverRegistry {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Remover> removers = new TreeMap<String, Remover>();

	public Map<String, Remover> getRemovers() {
		return removers;
	}

	public void addRemover(Remover remover) {

		if (StringUtils.isEmpty(remover.getKey())) {
			throw new RuntimeException("Remover Key is missing");
		}
		if (StringUtils.isEmpty(remover.getName())) {
			throw new RuntimeException("Remover Name is missing");
		}
		if (StringUtils.isEmpty(remover.getDescription())) {
			throw new RuntimeException("Remover Description is missing");
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Added '"+remover.getKey()+"' "+remover.getName());
		}
	
		removers.put(remover.getKey(), remover);
	}

	public void removeRemover(String key) {
		removers.remove(key);
	}
}
