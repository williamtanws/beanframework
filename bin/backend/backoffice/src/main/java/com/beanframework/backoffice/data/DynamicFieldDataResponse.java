package com.beanframework.backoffice.data;

import com.beanframework.common.data.DataTableResponseData;

public class DynamicFieldDataResponse extends DataTableResponseData {

	private String type;
	private String sort;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
