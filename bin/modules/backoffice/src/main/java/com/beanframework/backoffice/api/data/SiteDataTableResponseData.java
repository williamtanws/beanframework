package com.beanframework.backoffice.api.data;

import com.beanframework.core.data.DataTableResponseData;

public class SiteDataTableResponseData extends DataTableResponseData {

	private String url;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
