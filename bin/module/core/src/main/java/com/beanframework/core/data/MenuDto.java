package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuTargetTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4321844731565401047L;

	private String name;

	private Integer sort;

	private String icon;

	private String path;

	private MenuTargetTypeEnum target;

	private Boolean enabled;

	@JsonIgnore
	private Menu parent;

	@JsonIgnore
	private List<MenuDto> childs = new ArrayList<MenuDto>();

	private List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();

	private List<MenuFieldDto> fields = new ArrayList<MenuFieldDto>();

	private Boolean active;

	private String[] tableUserGroups;

	private String[] tableSelectedUserGroups;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public MenuTargetTypeEnum getTarget() {
		return target;
	}

	public void setTarget(MenuTargetTypeEnum target) {
		this.target = target;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<MenuDto> getChilds() {
		return childs;
	}

	public void setChilds(List<MenuDto> childs) {
		this.childs = childs;
	}

	public List<UserGroupDto> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroupDto> userGroups) {
		this.userGroups = userGroups;
	}

	public List<MenuFieldDto> getFields() {
		return fields;
	}

	public void setFields(List<MenuFieldDto> fields) {
		this.fields = fields;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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
