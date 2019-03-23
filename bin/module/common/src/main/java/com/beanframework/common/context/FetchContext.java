package com.beanframework.common.context;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class FetchContext {

	private Set<String> fetchProperties = new HashSet<String>();

	public Set<String> getFetchProperties() {
		return fetchProperties;
	}

	public void setFetchProperties(Set<String> fetchProperties) {
		this.fetchProperties = fetchProperties;
	}
}
