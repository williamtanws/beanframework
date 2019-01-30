package com.beanframework.common.converter;

public class ModelAction {

	private boolean initializeCollection = false;

	public boolean isInitializeCollection() {
		return initializeCollection;
	}

	public void setInitializeCollection(boolean initializeLazyCollection) {
		this.initializeCollection = initializeLazyCollection;
	}
}
