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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.UserGroupConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserGroupConstants.Table.USER_GROUP)
public class UserGroup extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5960532156682901612L;
	public static final String NAME = "name";
	public static final String USER_GROUPS = "userGroups";
	public static final String USER_AUTHORITIES = "userAuthorities";
	public static final String FIELDS = "fields";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = UserGroupConstants.Table.USER_GROUP_USER_GROUP_REL, joinColumns = @JoinColumn(name = "uuid", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "usergroup_uuid", referencedColumnName = "uuid"))
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();

	@AuditMappedBy(mappedBy = UserAuthority.USER_GROUP)
	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserAuthority.USER_GROUP, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<UserAuthority> userAuthorities = new ArrayList<UserAuthority>();

	@AuditMappedBy(mappedBy = UserGroupField.USER_GROUP)
	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserGroupField.USER_GROUP, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy(UserGroupField.DYNAMIC_FIELD)
	private List<UserGroupField> fields = new ArrayList<UserGroupField>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
}
