package com.beanframework.dynamicfield.domain;

public enum DynamicFieldTypeEnum {
	INTEGER("Integer"),
	FLOATING_POINT("Floating Point"),
	TEXT("Text"),
	SELECT("Select"),
	BOOLEAN("Boolean"),
	DATE("Date"),
	FILE("File");

	private String type;

	DynamicFieldTypeEnum(String type) {
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
