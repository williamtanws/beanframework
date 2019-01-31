package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class MenuFieldDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3192301047685937164L;

	private DynamicFieldDto dynamicField;

	private String value;

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
