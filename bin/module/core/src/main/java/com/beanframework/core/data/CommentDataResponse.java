package com.beanframework.core.data;

import com.beanframework.common.data.DataTableResponseData;

public class CommentDataResponse extends DataTableResponseData {

	private String user;
	private String html;
	private Boolean visibled;
	private String lastUpdatedDate;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Boolean getVisibled() {
		return visibled;
	}

	public void setVisibled(Boolean visibled) {
		this.visibled = visibled;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}
