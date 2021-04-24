package com.beanframework.core.data;

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

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
