package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;

public class UserGroupDto extends GenericDto {
	public static final String NAME = "name";
	public static final String USER_GROUPS = "userGroups";
	public static final String USER_AUTHORITIES = "userAuthorities";
	public static final String FIELDS = "fields";

	private String name;

	private List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();

	private List<UserAuthorityDto> userAuthorities = new ArrayList<UserAuthorityDto>();

	private List<UserGroupFieldDto> fields = new ArrayList<UserGroupFieldDto>();

	private String[] tableUserGroups;

	private String[] tableSelectedUserGroups;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserGroupDto> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroupDto> userGroups) {
		this.userGroups = userGroups;
	}

	public List<UserAuthorityDto> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(List<UserAuthorityDto> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public List<UserGroupFieldDto> getFields() {
		return fields;
	}

	public void setFields(List<UserGroupFieldDto> fields) {
		this.fields = fields;
	}

	public String[] getTableUserGroups() {
		return tableUserGroups;
	}

	public void setTableUserGroups(String[] tableUserGroups) {
		this.tableUserGroups = tableUserGroups;
	}

	public String[] getTableSelectedUserGroups() {
		return tableSelectedUserGroups;
	}

	public void setTableSelectedUserGroups(String[] tableSelectedUserGroups) {
		this.tableSelectedUserGroups = tableSelectedUserGroups;
	}

}
