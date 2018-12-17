package com.beanframework.user.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.language.domain.Language;
import com.beanframework.user.UserRightConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserRightConstants.Table.USER_RIGHT_FIELD)
public class UserRightField extends GenericDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7666190244677961254L;
	public static final String DOMAIN = "UserRightField";
	public static final String LANGUAGE = "language";
	public static final String USER_RIGHT = "userRight";
	public static final String DYNAMIC_FIELD = "dynamicField";

	@NotAudited
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_uuid")
	private Language language;

	@NotAudited
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userRight_uuid")
	private UserRight userRight;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dynamicfield_uuid")
	private DynamicField dynamicField;

	private String label;
	private String value;

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

	public DynamicField getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicField dynamicField) {
		this.dynamicField = dynamicField;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
