package com.beanframework.backoffice.data;

import com.beanframework.common.data.GenericDto;

public class UserAuthorityDto extends GenericDto {

	private UserGroupDto userGroup;

	private UserPermissionDto userPermission;

	private UserRightDto userRight;

	private Boolean enabled;

	public UserGroupDto getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroupDto userGroup) {
		this.userGroup = userGroup;
	}

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
