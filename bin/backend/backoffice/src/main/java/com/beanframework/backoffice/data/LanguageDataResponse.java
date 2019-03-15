package com.beanframework.backoffice.data;

import com.beanframework.common.data.DataTableResponseData;

public class LanguageDataResponse extends DataTableResponseData {

	private Boolean active;
	private Integer sort;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
