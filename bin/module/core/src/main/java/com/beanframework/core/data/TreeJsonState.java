package com.beanframework.core.data;

public class TreeJsonState {

	private boolean selected = false;

	public TreeJsonState(boolean selected) {
		super();
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
