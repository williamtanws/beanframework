package com.beanframework.backoffice.api.data;

import com.beanframework.core.data.DataTableResponseData;

public class CustomerDataTableResponseData extends DataTableResponseData {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}