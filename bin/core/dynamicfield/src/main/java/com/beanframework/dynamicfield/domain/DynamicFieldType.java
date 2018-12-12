package com.beanframework.dynamicfield.domain;

public enum DynamicFieldType {
	INTEGER("Integer"),
	FLOATING_POINT("Floating Point"),
	TEXT("Text"),
	BOOLEAN("Boolean"),
	DATE("Date");

	private String type;

	DynamicFieldType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
