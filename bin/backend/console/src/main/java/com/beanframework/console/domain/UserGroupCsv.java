package com.beanframework.console.domain;

public class UserGroupCsv extends AbstractCsv {

	private String userGroupIds;
	private String dynamicField;

	public String getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(String userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	public String getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(String dynamicField) {
		this.dynamicField = dynamicField;
	}

}
