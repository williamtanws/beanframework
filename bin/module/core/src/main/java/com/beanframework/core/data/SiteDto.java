package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class SiteDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7727760714770195858L;
	public static final String NAME = "name";
	public static final String URL = "url";

	private String name;
	private String url;

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
