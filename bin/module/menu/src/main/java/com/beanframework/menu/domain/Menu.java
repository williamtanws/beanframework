package com.beanframework.menu.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.menu.MenuConstants;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = MenuConstants.Table.MENU)
public class Menu extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -443408642731245353L;
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

	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_uuid")
	private Menu parent;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "parent")
	@OrderBy(SORT + " ASC")
	private List<Menu> childs = new ArrayList<Menu>();
	
	@Audited(withModifiedFlag = true)
	@ElementCollection
	@CollectionTable(name = MenuConstants.Table.MENU_USER_GROUP_REL, joinColumns = @JoinColumn(name = "menu_uuid"))
	@Column(name = "usergrou_uuid", columnDefinition = "BINARY(16)", nullable = false)
	private Set<UUID> userGroups = new HashSet<UUID>();

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@OrderBy(MenuField.DYNAMIC_FIELD_SLOT)
	private List<MenuField> fields = new ArrayList<MenuField>();

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

	public Set<UUID> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UUID> userGroups) {
		this.userGroups = userGroups;
	}

	public List<MenuField> getFields() {
		return fields;
	}

	public void setFields(List<MenuField> fields) {
		this.fields = fields;
	}
}
