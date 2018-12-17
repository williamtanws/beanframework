package com.beanframework.menu.domain;

public enum MenuTargetType {
	BLANK("_blank"),
	SELF("_self"),
	PARENT("_parent"),
	TOP("_top"),
	FRAMENAME("framename");

	private String type;

	MenuTargetType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
