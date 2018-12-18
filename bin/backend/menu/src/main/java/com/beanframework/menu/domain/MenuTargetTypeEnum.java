package com.beanframework.menu.domain;

public enum MenuTargetTypeEnum {
	BLANK("_blank"),
	SELF("_self"),
	PARENT("_parent"),
	TOP("_top"),
	FRAMENAME("framename");

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
