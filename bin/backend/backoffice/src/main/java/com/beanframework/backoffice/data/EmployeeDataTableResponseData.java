package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class EmployeeDataTableResponseData extends DataTableResponseData {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}