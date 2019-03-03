package com.beanframework.core.data;

import org.springframework.web.multipart.MultipartFile;

public class EmployeeDto extends UserDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4737285340190955043L;

	private String[] tableUserGroups;

	private String[] tableSelectedUserGroups;
	
	private MultipartFile profilePicture;

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

	public MultipartFile getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(MultipartFile profilePicture) {
		this.profilePicture = profilePicture;
	}

}
