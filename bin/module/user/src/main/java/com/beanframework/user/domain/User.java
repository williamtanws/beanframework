package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.UserConstants;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserConstants.Table.USER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class User extends GenericEntity {

	public static final String PASSWORD = "password";
	public static final String ACCOUNT_NON_EXPIRED = "accountNonExpired";
	public static final String ACCOUNT_NON_LOCKED = "accountNonLocked";
	public static final String CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
	public static final String ENABLED = "enabled";
	public static final String USER_GROUPS = "userGroups";
	public static final String USER_ROLES = "userRoles";
	public static final String FIELDS = "fields";
	public static final String NAME = "name";

	private static final long serialVersionUID = -7444894280894062710L;

	@Column(insertable = false, updatable = false)
	private String type;

	@Audited(withModifiedFlag = true)
	@Column(length = 60)
	private String password;

	@Audited(withModifiedFlag = true)
	private Boolean accountNonExpired;

	@Audited(withModifiedFlag = true)
	private Boolean accountNonLocked;

	@Audited(withModifiedFlag = true)
	private Boolean credentialsNonExpired;

	@Audited(withModifiedFlag = true)
	private Boolean enabled;

	@Audited(withModifiedFlag = true)
	private String name;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@AuditJoinTable(inverseJoinColumns = @JoinColumn(name = "usergroup_uuid"))
	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = UserConstants.Table.USER_USER_GROUP_REL, joinColumns = @JoinColumn(name = "user_uuid", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "usergroup_uuid", referencedColumnName = "uuid"))
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@OrderBy(UserField.DYNAMIC_FIELD_SLOT)
	private List<UserField> fields = new ArrayList<UserField>();

	@Transient
	private String profilePicture;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

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

	public List<UserField> getFields() {
		return fields;
	}

	public void setFields(List<UserField> fields) {
		this.fields = fields;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

}
