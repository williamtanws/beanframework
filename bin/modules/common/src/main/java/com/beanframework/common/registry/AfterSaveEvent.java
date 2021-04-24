package com.beanframework.common.registry;

public class AfterSaveEvent {

	public static final int UPDATE = 1;
	public static final int CREATE = 2;

	private final int type;

	public AfterSaveEvent(int type) {
		if (1 != type && 2 != type) {
			throw new IllegalArgumentException("wrong operation type");
		} else {
			this.type = type;
		}
	}

	public int getType() {
		return type;
	}

	public String toString() {
		return "AfterSave[type:" + this.getTypeAsString() + "]";
	}

	private String getTypeAsString() {
		switch (this.type) {
		case 1:
			return "UPDATE";
		case 2:
			return "CREATE";
		default:
			return "UNKNOWN<" + this.type + ">";
		}
	}

}
