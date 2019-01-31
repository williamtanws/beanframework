package com.beanframework.core.data;

public class CustomerDto extends UserDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7514300671142954145L;

	private String[] tableUserGroups;

	private String[] tableSelectedUserGroups;

	public String[] getTableUserGroups() {
		return tableUserGroups;
	}

	public void setTableUserGroups(String[] tableUserGroups) {
		this.tableUserGroups = tableUserGroups;
	}

	public String[] getTableSelectedUserGroups() {
		return tableSelectedUserGroups;
	}

	public void setTableSelectedUserGroups(String[] tableSelectedUserGroups) {
		this.tableSelectedUserGroups = tableSelectedUserGroups;
	}

}
