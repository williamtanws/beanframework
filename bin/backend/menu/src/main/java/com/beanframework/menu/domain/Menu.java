package com.beanframework.menu.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.menu.MenuConstants;
import com.beanframework.user.domain.UserGroup;

@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
@Table(name = MenuConstants.Table.MENU)
public class Menu extends GenericEntity {

	private static final long serialVersionUID = 8293422057240349702L;
	public static final String NAME = "name";
	public static final String SORT = "sort";
	public static final String DESCRIPTION = "description";
	public static final String ICON = "icon";
	public static final String PATH = "path";
	public static final String TARGET = "target";
	public static final String ENABLED = "enabled";
	public static final String PARENT = "parent";
	public static final String PARENT_UUID = "parent.uuid";
	public static final String CHILDS = "childs";
	public static final String USER_GROUPS = "userGroups";
	public static final String USER_GROUPS_UUID = "userGroups.uuid";
	public static final String FIELDS = "fields";
	
	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	@NotNull
	private Integer sort;

	@Audited(withModifiedFlag = true)
	private String icon;

	@Audited(withModifiedFlag = true)
	private String path;

	@Audited(withModifiedFlag = true)
	@Enumerated(EnumType.STRING)
	private MenuTargetTypeEnum target;

	@Audited(withModifiedFlag = true)
	@NotNull
	private Boolean enabled;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_uuid")
	private Menu parent;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = PARENT, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy(SORT + " ASC")
	private List<Menu> childs = new ArrayList<Menu>();

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = MenuConstants.Table.MENU_USER_GROUP_REL, joinColumns = @JoinColumn(name = "menu_uuid", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "usergroup_uuid", referencedColumnName = "uuid"))
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = MenuField.MENU, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy(MenuField.DYNAMIC_FIELD)
	private List<MenuField> fields = new ArrayList<MenuField>();

	

	@Transient
	private Boolean active;

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

	public List<Menu> getChilds() {
		return childs;
	}

	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<MenuField> getFields() {
		return fields;
	}

	public void setFields(List<MenuField> fields) {
		this.fields = fields;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
