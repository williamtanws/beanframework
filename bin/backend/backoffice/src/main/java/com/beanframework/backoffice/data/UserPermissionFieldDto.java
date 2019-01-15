package com.beanframework.backoffice.data;

import com.beanframework.common.data.GenericDto;

public class UserPermissionFieldDto extends GenericDto {

	private UserPermissionDto userPermission;

	private DynamicFieldDto dynamicField;

	private String value;

	public UserPermissionDto getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(UserPermissionDto userPermission) {
		this.userPermission = userPermission;
	}

	public DynamicFieldDto getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicFieldDto dynamicField) {
		this.dynamicField = dynamicField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
