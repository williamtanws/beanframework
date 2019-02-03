package com.beanframework.backoffice.data;

import com.beanframework.common.data.DataTableResponseData;

public class LanguageDataResponse extends DataTableResponseData {

	private Boolean active;
	private String sort;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
