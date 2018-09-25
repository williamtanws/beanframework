package com.beanframework.console.registry;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.domain.Initializer;

@Component
public class InitializerRegistry {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Initializer> initializers = new TreeMap<String, Initializer>();

	public Map<String, Initializer> getInitializers() {
		return initializers;
	}

	public void addInitializer(Initializer initializer) {

		if (StringUtils.isEmpty(initializer.getKey())) {
			throw new RuntimeException("Initializer Key is missing");
		}
		if (StringUtils.isEmpty(initializer.getName())) {
			throw new RuntimeException("Initializer Name is missing");
		}
		if (StringUtils.isEmpty(initializer.getDescription())) {
			throw new RuntimeException("Initializer Description is missing");
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Added '"+initializer.getKey()+"' "+initializer.getName());
		}
	
		initializers.put(initializer.getKey(), initializer);
	}

	public void removeInitializer(String key) {
		initializers.remove(key);
	}
}
