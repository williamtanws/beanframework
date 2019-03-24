package com.beanframework.common.context;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterceptorContext {

	@Autowired
	private FetchContext fetchContext;
	
	public Set<String> getFetchProperties() {
		return fetchContext.getFetchProperties();
	}
	
	public void addFetchProperty(String property) {
		fetchContext.getFetchProperties().add(property);
	}

	public void clearFetchProperties() {
		fetchContext.getFetchProperties().clear();
	}
	
	public boolean isFetchable(String property) {
		return fetchContext.getFetchProperties().contains(property);
	}

}
