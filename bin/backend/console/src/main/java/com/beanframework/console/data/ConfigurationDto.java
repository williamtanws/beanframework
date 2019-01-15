package com.beanframework.console.data;

import com.beanframework.common.data.GenericDto;

public class ConfigurationDto extends GenericDto {
	public static final String VALUE = "value";
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
