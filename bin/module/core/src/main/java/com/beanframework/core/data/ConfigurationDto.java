package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class ConfigurationDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7350422369857009359L;

	public static final String VALUE = "value";
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
