package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class UserFieldDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1300411859602574005L;

	private UserDto user;

	private DynamicFieldSlotDto dynamicFieldSlot;

	private String value;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public DynamicFieldSlotDto getDynamicFieldSlot() {
		return dynamicFieldSlot;
	}

	public void setDynamicFieldSlot(DynamicFieldSlotDto dynamicFieldSlot) {
		this.dynamicFieldSlot = dynamicFieldSlot;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
