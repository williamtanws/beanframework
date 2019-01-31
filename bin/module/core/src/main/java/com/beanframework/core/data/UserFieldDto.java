package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class UserFieldDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1300411859602574005L;

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
