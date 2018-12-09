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
@Table(name = UserConstants.Table.USER_GROUP_LANG)
public class UserGroupLang extends AbstractDomain {

	private static final long serialVersionUID = 3253296818921675586L;
	public static final String MODEL = "UserGroupLang";
	public static final String NAME = "name";
	public static final String LANGUAGE = "language";
	public static final String USER_GROUP = "userGroup";

	@NotAudited
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_uuid")
	private Language language;

	@NotAudited
	@NotNull
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usergroup_uuid")
	private UserGroup userGroup;

	private String name;

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
