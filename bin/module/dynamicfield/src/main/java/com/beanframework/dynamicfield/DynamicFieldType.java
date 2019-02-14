package com.beanframework.dynamicfield;

public enum DynamicFieldType {
	INTEGER("Integer"),
	FLOATING_POINT("Floating Point"),
	TEXT("Text"),
	SELECT("Select"),
	BOOLEAN("Boolean"),
	DATE("Date"),
	FILE("File");

	private String type;

	DynamicFieldType(String type) {
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
