package com.beanframework.backoffice.data;

import com.beanframework.common.data.GenericDto;

public class UserGroupFieldDto extends GenericDto {

	private UserGroupDto userGroup;

	private DynamicFieldDto dynamicField;

	private String value;

	public UserGroupDto getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroupDto userGroup) {
		this.userGroup = userGroup;
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
