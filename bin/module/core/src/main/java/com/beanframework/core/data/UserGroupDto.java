package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;

public class UserGroupDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6153206915173481326L;
	public static final String NAME = "name";
	public static final String USER_GROUPS = "userGroups";
	public static final String USER_AUTHORITIES = "userAuthorities";
	public static final String FIELDS = "fields";

	private String name;

	private List<UserDto> users = new ArrayList<UserDto>();

	private List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();

	private List<UserAuthorityDto> userAuthorities = new ArrayList<UserAuthorityDto>();

	private List<UserGroupFieldDto> fields = new ArrayList<UserGroupFieldDto>();

	private String[] selectedUserGroups;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
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

	public String[] getSelectedUserGroups() {
		return selectedUserGroups;
	}

	public void setSelectedUserGroups(String[] selectedUserGroups) {
		this.selectedUserGroups = selectedUserGroups;
	}

}
