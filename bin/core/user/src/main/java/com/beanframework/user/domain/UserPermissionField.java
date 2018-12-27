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
import com.beanframework.user.UserPermissionConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserPermissionConstants.Table.USER_PERMISSION_FIELD)
public class UserPermissionField extends GenericDomain {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4279536635924738476L;
	public static final String LANGUAGE = "language";
	public static final String USER_RIGHT = "userPermission";
	public static final String DYNAMIC_FIELD = "dynamicField";
	
	@NotAudited
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userPermission_uuid")
	private UserPermission userPermission;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dynamicfield_uuid")
	private DynamicField dynamicField;

	private String value;

	public UserPermission getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(UserPermission userPermission) {
		this.userPermission = userPermission;
	}

	public DynamicField getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicField dynamicField) {
		this.dynamicField = dynamicField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
