package com.beanframework.user.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.UserGroupConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserGroupConstants.Table.USER_GROUP_ATTRIBUTE)
public class UserGroupAttribute extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -115811375029158266L;
	public static final String USER_GROUP = "userGroup";
	public static final String DYNAMIC_FIELD_SLOT = "dynamicFieldSlot";
	
	@JsonIgnore
	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usergroup_uuid")
	private UserGroup userGroup;

	@Audited(withModifiedFlag = true)
	@Column(name = "dynamicfieldslot_uuid", columnDefinition = "BINARY(16)")
	private UUID dynamicFieldSlot;

	@Audited(withModifiedFlag = true)
	private String value;

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public UUID getDynamicFieldSlot() {
		return dynamicFieldSlot;
	}

	public void setDynamicFieldSlot(UUID dynamicFieldSlot) {
		this.dynamicFieldSlot = dynamicFieldSlot;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
