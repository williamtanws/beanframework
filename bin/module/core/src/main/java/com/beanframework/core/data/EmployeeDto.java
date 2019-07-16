package com.beanframework.core.data;

import org.springframework.web.multipart.MultipartFile;

public class EmployeeDto extends UserDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4737285340190955043L;

	private String[] selectedUserGroups;

	private MultipartFile profilePicture;

	public String[] getSelectedUserGroups() {
		return selectedUserGroups;
	}

	public void setSelectedUserGroups(String[] selectedUserGroups) {
		this.selectedUserGroups = selectedUserGroups;
	}

	public MultipartFile getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(MultipartFile profilePicture) {
		this.profilePicture = profilePicture;
	}

}
