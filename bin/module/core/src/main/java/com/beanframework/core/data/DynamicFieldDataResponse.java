package com.beanframework.core.data;

import com.beanframework.common.data.DataTableResponseData;

public class DynamicFieldDataResponse extends DataTableResponseData {

	private String type;
	private Integer sort;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
