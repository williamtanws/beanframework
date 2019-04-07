package com.beanframework.console.data;

import com.beanframework.core.data.DataTableResponseData;

public class ConfigurationDataResponse extends DataTableResponseData {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
