package com.beanframework.common.context;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class FetchContext {

	public void addFetchProperty(Class<?> modelClass, String property) {
		if (FetchProperties.getInstance().getProperties().get(modelClass.getName()) == null) {
			Set<String> newProperties = new HashSet<String>();
			newProperties.add(property);
			FetchProperties.getInstance().getProperties().put(modelClass.getName(), newProperties);
		} else {
			FetchProperties.getInstance().getProperties().get(modelClass.getName()).add(property);
		}
	}

	public boolean isFetchable(Class<?> modelClass, String property) {
		if (FetchProperties.getInstance().getProperties().get(modelClass.getName()) != null) {
			return FetchProperties.getInstance().getProperties().get(modelClass.getName()).contains(property);
		} else {
			return false;
		}
	}

	public void clearFetchProperties() {
		FetchProperties.getInstance().getProperties().clear();
	}
}
