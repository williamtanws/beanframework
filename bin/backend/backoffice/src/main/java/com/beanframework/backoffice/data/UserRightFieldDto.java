package com.beanframework.backoffice.data;

import com.beanframework.common.data.GenericDto;

public class UserRightFieldDto extends GenericDto {

	private UserRightDto userRight;

	private DynamicFieldDto dynamicField;

	private String value;

	public UserRightDto getUserRight() {
		return userRight;
	}

	public void setUserRight(UserRightDto userRight) {
		this.userRight = userRight;
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
