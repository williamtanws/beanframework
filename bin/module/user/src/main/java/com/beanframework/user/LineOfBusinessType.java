package com.beanframework.user;

public enum LineOfBusinessType {
	TRADE("Trade"), 
	BANK("Bank"), 
	INDUSTRY("Industry"), 
	BUILDING("Building"),
	GOVERMENT("Gorvement"),
	SERVICE("Service");
	
	private String type;

	LineOfBusinessType(String type) {
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
