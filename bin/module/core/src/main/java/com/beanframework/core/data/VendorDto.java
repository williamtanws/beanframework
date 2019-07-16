package com.beanframework.core.data;

import org.springframework.web.multipart.MultipartFile;

public class VendorDto extends UserDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7514300671142954145L;

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
