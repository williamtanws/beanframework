package com.beanframework.common.converter;

import java.util.HashSet;
import java.util.Set;

public class ModelAction {

	private boolean initializeCollection = false;

	private Set<String> propertyToInitialize = new HashSet<String>();

	public boolean isInitializeCollection() {
		return initializeCollection;
	}

	public void setInitializeCollection(boolean initializeLazyCollection) {
		this.initializeCollection = initializeLazyCollection;
	}

	public Set<String> getPropertyToInitialize() {
		return propertyToInitialize;
	}

	public void setPropertyToInitialize(Set<String> propertyToInitialize) {
		this.propertyToInitialize = propertyToInitialize;
	}
}
