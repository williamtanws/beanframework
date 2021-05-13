package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;
import com.beanframework.common.data.GenericDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

  private List<UserGroupAttributeDto> attributes = new ArrayList<UserGroupAttributeDto>();

  @JsonIgnore
  private String[] selectedUserGroupUuids;

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

  public List<UserGroupAttributeDto> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<UserGroupAttributeDto> attributes) {
    this.attributes = attributes;
  }

  public String[] getSelectedUserGroupUuids() {
    return selectedUserGroupUuids;
  }

  public void setSelectedUserGroupUuids(String[] selectedUserGroupUuids) {
    this.selectedUserGroupUuids = selectedUserGroupUuids;
  }

}
