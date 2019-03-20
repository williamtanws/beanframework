package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	private CommentDto repliedTo;

	private List<CommentDto> repliedBys = new ArrayList<CommentDto>();

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

	public CommentDto getRepliedTo() {
		return repliedTo;
	}

	public void setRepliedTo(CommentDto repliedTo) {
		this.repliedTo = repliedTo;
	}

	public List<CommentDto> getRepliedBys() {
		return repliedBys;
	}

	public void setRepliedBys(List<CommentDto> repliedBys) {
		this.repliedBys = repliedBys;
	}

}
