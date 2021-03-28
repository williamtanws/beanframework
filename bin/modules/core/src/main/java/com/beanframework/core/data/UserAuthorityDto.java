package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class UserAuthorityDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -865505469742305954L;

	private UserPermissionDto userPermission;

	private UserRightDto userRight;

	private Boolean enabled;

	public UserPermissionDto getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(UserPermissionDto userPermission) {
		this.userPermission = userPermission;
	}

	public UserRightDto getUserRight() {
		return userRight;
	}

	public void setUserRight(UserRightDto userRight) {
		this.userRight = userRight;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
