package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class BackofficeConfigurationDto extends GenericDto {
	public static final String VALUE = "value";
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
