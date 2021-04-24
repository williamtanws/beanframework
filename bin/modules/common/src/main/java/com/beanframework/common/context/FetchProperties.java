package com.beanframework.common.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FetchProperties {

	private static volatile FetchProperties instance;

	private static Map<String, Set<String>> properties = new HashMap<String, Set<String>>();

	private FetchProperties() {
	}

	public static FetchProperties getInstance() {
		FetchProperties result = instance;
		if (result == null) {
			synchronized (properties) {
				result = instance;
				if (result == null)
					instance = result = new FetchProperties();
			}
		}
		return result;
	}

	public Map<String, Set<String>> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Set<String>> properties) {
		FetchProperties.properties = properties;
	}

}
