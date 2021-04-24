package com.beanframework.imex;

public enum ImexType {
	IMPORT("Import"), EXPORT("Export");

	private String type;

	ImexType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return type;
	}
}
