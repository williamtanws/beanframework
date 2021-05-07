package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class MenuAttributeDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3192301047685937164L;

	private DynamicFieldSlotDto dynamicFieldSlot;

	private String value;

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
