package com.beanframework.console.registry;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImporterRegistry {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImporterRegistry.class);

	private Map<String, Importer> importers = new TreeMap<String, Importer>();

	public Map<String, Importer> getImporters() {
		return importers;
	}

	public void addImporter(Importer importer) {

		if (StringUtils.isBlank(importer.getKey())) {
			throw new RuntimeException("importer Key is missing");
		}
		if (StringUtils.isBlank(importer.getName())) {
			throw new RuntimeException("importer Name is missing");
		}
		if (StringUtils.isBlank(importer.getDescription())) {
			throw new RuntimeException("importer Description is missing");
		}
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Added '"+importer.getKey()+"' "+importer.getName());
		}
	
		importers.put(importer.getKey(), importer);
	}

	public void removeImporter(String key) {
		importers.remove(key);
	}
}
