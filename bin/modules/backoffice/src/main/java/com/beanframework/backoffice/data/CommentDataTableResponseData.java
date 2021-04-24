package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.UserDto;

public class CommentDataTableResponseData extends DataTableResponseData {

	private UserDto user;
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

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}
