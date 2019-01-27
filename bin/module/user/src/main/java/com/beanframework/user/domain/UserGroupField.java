package com.beanframework.user.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.UserGroupConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Audited
@Table(name = UserGroupConstants.Table.USER_GROUP_FIELD)
public class UserGroupField extends GenericEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -115811375029158266L;
	public static final String LANGUAGE = "language";
	public static final String USER_GROUP = "userGroup";
	public static final String DYNAMIC_FIELD = "dynamicField";
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usergroup_uuid")
	private UserGroup userGroup;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dynamicfield_uuid")
	@OrderBy(DynamicField.SORT+" ASC")
	private DynamicField dynamicField;

	@Audited(withModifiedFlag = true)
	private String value;

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
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
