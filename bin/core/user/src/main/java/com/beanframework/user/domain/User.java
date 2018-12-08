package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.user.UserConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = UserConstants.Table.USER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class User extends AbstractDomain {

	public static final String MODEL = "User";
	public static final String PASSWORD = "password";
	public static final String ACCOUNT_NON_EXPIRED = "accountNonExpired";
	public static final String ACCOUNT_NON_LOCKED = "accountNonLocked";
	public static final String CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
	public static final String ENABLED = "enabled";
	public static final String USER_GROUPS = "userGroups";
	public static final String USER_ROLES = "userRoles";

	private static final long serialVersionUID = -7444894280894062710L;
	@NotNull
	private String password;
	@NotNull
	private Boolean accountNonExpired;
	@NotNull
	private Boolean accountNonLocked;
	@NotNull
	private Boolean credentialsNonExpired;
	@NotNull
	private Boolean enabled;
	
	@Cascade({CascadeType.REFRESH})
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = UserConstants.Table.USER_GROUP_REL, joinColumns = @JoinColumn(name = "user_uuid", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "usergroup_uuid", referencedColumnName = "uuid"))
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();

	@Transient
	private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}
}