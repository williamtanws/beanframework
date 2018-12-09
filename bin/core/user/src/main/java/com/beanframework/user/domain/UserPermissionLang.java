package com.beanframework.user.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.language.domain.Language;
import com.beanframework.user.UserConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Audited
@Table(name = UserConstants.Table.USER_PERMISSION_LANG)
public class UserPermissionLang extends AbstractDomain {

	private static final long serialVersionUID = 7972346966663224590L;
	public static final String MODEL = "PermissionLang";
	public static final String NAME = "name";
	public static final String LANGUAGE = "language";
	public static final String USER_PERMISSION = "userPermission";

	@NotAudited
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_uuid")
	private Language language;

	@NotNull
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userpermission_uuid")
	private UserPermission userPermission;

	private String name;

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public UserPermission getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(UserPermission userPermission) {
		this.userPermission = userPermission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
