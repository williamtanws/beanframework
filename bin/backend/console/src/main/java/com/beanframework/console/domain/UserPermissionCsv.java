package com.beanframework.console.domain;

public class UserPermissionCsv extends AbstractCsv {

	private int sort;
	private String dynamicField;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(String dynamicField) {
		this.dynamicField = dynamicField;
	}
}
