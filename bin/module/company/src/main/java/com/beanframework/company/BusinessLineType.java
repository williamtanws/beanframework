package com.beanframework.company;

public enum BusinessLineType {
	TRADE("Trade"), 
	BANK("Bank"), 
	INDUSTRY("Industry"), 
	BUILDING("Building"),
	GOVERMENT("Gorvement"),
	SERVICE("Service");
	
	private String type;

	BusinessLineType(String type) {
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
