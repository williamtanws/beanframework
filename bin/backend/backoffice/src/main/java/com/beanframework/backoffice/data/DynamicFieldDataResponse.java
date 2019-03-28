package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class DynamicFieldDataResponse extends DataTableResponseData {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
