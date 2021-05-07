package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.UserConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	public static final String COMPANIES = "companies";
	public static final String ADDRESSES = "addresses";
	public static final String ATTRIBUTES = "fields";
	public static final String NAME = "name";

	private static final long serialVersionUID = -7444894280894062710L;

	@Column(insertable = false, updatable = false)
	private String type;

	@JsonIgnore
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

	@Audited(withModifiedFlag = true)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = UserConstants.Table.USER_USER_GROUP_REL, joinColumns = @JoinColumn(name = "user_uuid"))
	@Column(name = "usergroup_uuid", columnDefinition = "BINARY(16)", nullable = false)
	private Set<UUID> userGroups = new HashSet<UUID>();

	@Audited(withModifiedFlag = true)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = UserConstants.Table.USER_COMPANY_REL, joinColumns = @JoinColumn(name = "user_uuid"))
	@Column(name = "company_uuid", columnDefinition = "BINARY(16)", nullable = false)
	private Set<UUID> companies = new HashSet<UUID>();

	@Audited(withModifiedFlag = true)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = UserConstants.Table.USER_ADDRESS_REL, joinColumns = @JoinColumn(name = "user_uuid"))
	@Column(name = "address_uuid", columnDefinition = "BINARY(16)", nullable = false)
	private Set<UUID> addresses = new HashSet<UUID>();

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@OrderBy(UserAttribute.DYNAMIC_FIELD_SLOT)
	private List<UserAttribute> attributes = new ArrayList<UserAttribute>();

	@Audited(withModifiedFlag = true)
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@CollectionTable(name = UserConstants.Table.USER_PARAMETER, joinColumns = @JoinColumn(name = "user_uuid"))
	Map<String, String> parameters = new HashMap<String, String>();

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

	public Set<UUID> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UUID> userGroups) {
		this.userGroups = userGroups;
	}

	public List<UserAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UserAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Set<UUID> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<UUID> companies) {
		this.companies = companies;
	}

	public Set<UUID> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<UUID> addresses) {
		this.addresses = addresses;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
