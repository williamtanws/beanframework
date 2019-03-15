package com.beanframework.menu.domain;

public enum MenuTargetTypeEnum {
	SELF("_self"), BLANK("_blank"), PARENT("_parent"), TOP("_top"), FRAMENAME("framename");

	private String type;

	MenuTargetTypeEnum(String type) {
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
