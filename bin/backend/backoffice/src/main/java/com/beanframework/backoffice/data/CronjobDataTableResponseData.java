package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class CronjobDataTableResponseData extends DataTableResponseData {

	private String name;
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
