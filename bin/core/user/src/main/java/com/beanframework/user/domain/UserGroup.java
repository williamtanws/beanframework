package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.user.UserGroupConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserGroupConstants.Table.USER_GROUP)
public class UserGroup extends GenericDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5960532156682901612L;
	public static final String PARENT = "parent";
	public static final String PARENTS = "parents";
	public static final String CHILDS = "childs";
	public static final String USER_AUTHORITIES = "userAuthorities";
	public static final String FIELDS = "fields";

	@Cascade({ CascadeType.REFRESH })
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = UserGroupConstants.Table.USER_GROUP_USER_GROUP_REL, joinColumns = @JoinColumn(name = "uuid", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "usergroup_uuid", referencedColumnName = "uuid"))
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserAuthority.USER_GROUP, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<UserAuthority> userAuthorities = new ArrayList<UserAuthority>();

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserGroupField.USER_GROUP, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy(UserGroupField.DYNAMIC_FIELD)
	private List<UserGroupField> fields = new ArrayList<UserGroupField>();

	@Transient
	private String selected;

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(List<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public List<UserGroupField> getFields() {
		return fields;
	}

	public void setFields(List<UserGroupField> fields) {
		this.fields = fields;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
