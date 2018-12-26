package com.beanframework.console.data;

public class MenuCsv extends AbstractCsv {

	private int sort;
	private String icon;
	private String path;
	private String target;
	private boolean enabled;
	private String parent;
	private String userGroupIds;
	private String dynamicField;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

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
