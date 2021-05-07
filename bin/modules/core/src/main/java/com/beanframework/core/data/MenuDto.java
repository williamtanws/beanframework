package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.data.GenericDto;
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
	private MenuDto parent;

	@JsonIgnore
	private List<MenuDto> childs = new ArrayList<MenuDto>();

	private List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();

	private List<MenuAttributeDto> attributes = new ArrayList<MenuAttributeDto>();

	private Boolean active;

	@JsonIgnore
	private String[] selectedUserGroupUuids;

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

	public MenuDto getParent() {
		return parent;
	}

	public void setParent(MenuDto parent) {
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

	public List<MenuAttributeDto> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<MenuAttributeDto> attributes) {
		this.attributes = attributes;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String[] getSelectedUserGroupUuids() {
		return selectedUserGroupUuids;
	}

	public void setSelectedUserGroupUuids(String[] selectedUserGroupUuids) {
		this.selectedUserGroupUuids = selectedUserGroupUuids;
	}

	public String getName(String languageId) {
		for (MenuAttributeDto attribute : attributes) {
			if (attribute.getDynamicFieldSlot().getId().endsWith("_name_" + languageId)) {
				if (attribute.getDynamicFieldSlot().getDynamicField().getLanguage() != null && attribute.getDynamicFieldSlot().getDynamicField().getLanguage().getId().equals(languageId)) {

					if (StringUtils.isBlank(attribute.getValue())) {
						return StringUtils.isBlank(this.getName()) ? "[" + this.getId() + "]" : this.getName();
					} else {
						return attribute.getValue();
					}
				}
			}
		}

		return StringUtils.isBlank(this.getName()) ? "[" + this.getId() + "]" : this.getName();
	}

	public String getHeader(String languageId) {
		for (MenuAttributeDto attribute : attributes) {
			if (attribute.getDynamicFieldSlot().getId().endsWith("_header_" + languageId)) {
				if (attribute.getDynamicFieldSlot().getDynamicField().getLanguage() != null && attribute.getDynamicFieldSlot().getDynamicField().getLanguage().getId().equals(languageId)) {

					if (StringUtils.isNotBlank(attribute.getValue())) {
						return attribute.getValue();
					}
				}
			}
		}

		return null;
	}
}
