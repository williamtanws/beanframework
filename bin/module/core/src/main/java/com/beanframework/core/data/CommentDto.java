package com.beanframework.core.data;

import java.util.Date;

import com.beanframework.common.data.GenericDto;

public class CommentDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7160397218234172945L;
	public static final String HTML = "html";
	public static final String VISIBLED = "visibled";

	private String html;

	private Boolean visibled;

	private Date lastUpdatedDate;

	private UserDto user;

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

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
