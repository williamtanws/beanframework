package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class DynamicFieldDataTableResponseData extends DataTableResponseData {

	private String type;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
