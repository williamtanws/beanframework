package com.beanframework.user.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.language.domain.Language;
import com.beanframework.user.UserConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = UserConstants.Table.USER_RIGHT_LANG)
public class UserRightLang extends AbstractDomain {

	private static final long serialVersionUID = 3253296818921675586L;
	public static final String MODEL = "UserRightLang";
	public static final String NAME = "name";
	public static final String LANGUAGE = "language";
	public static final String USER_RIGHT = "userRight";

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_uuid")
	private Language language;

	@NotNull
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userRight_uuid")
	private UserRight userRight;

	private String name;

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public UserRight getUserRight() {
		return userRight;
	}

	public void setUserRight(UserRight userRight) {
		this.userRight = userRight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
