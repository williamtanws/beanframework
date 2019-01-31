package com.beanframework.core.data;

public class EmployeeDto extends UserDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4737285340190955043L;

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
