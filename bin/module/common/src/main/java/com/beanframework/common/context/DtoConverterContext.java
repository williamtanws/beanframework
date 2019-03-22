package com.beanframework.common.context;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class DtoConverterContext {

	private Set<String> fetchProperties = new HashSet<String>();

	public Set<String> getFetchProperties() {
		return fetchProperties;
	}

	public void setFetchProperties(Set<String> fetchProperties) {
		this.fetchProperties = fetchProperties;
	}
	
	public void addFetchProperty(String property) {
		fetchProperties.add(property);
	}

	public void clearFetchProperties() {
		fetchProperties.clear();
	}

}
