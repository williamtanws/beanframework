package com.beanframework.common.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class FetchContext {

	private Map<String, Set<String>> fetchProperties = new HashMap<String, Set<String>>();

	public void addFetchProperty(Class<?> modelClass, String property) {
		if (fetchProperties.get(modelClass.getName()) == null) {
			Set<String> newProperties = new HashSet<String>();
			newProperties.add(property);
			fetchProperties.put(modelClass.getName(), newProperties);
		} else {
			fetchProperties.get(modelClass.getName()).add(property);
		}
	}

	public boolean isFetchable(Class<?> modelClass, String property) {
		if (fetchProperties.get(modelClass.getName()) != null) {
			return fetchProperties.get(modelClass.getName()).contains(property);
		} else {
			return false;
		}
	}

	public void clearFetchProperties(Class<?> modelClass) {
		fetchProperties = new HashMap<String, Set<String>>();
//		this.fetchProperties.remove(modelClass.getName());
	}
}
