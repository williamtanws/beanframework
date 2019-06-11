package com.beanframework.user.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.user.UserGroupConstants;

@Cacheable
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserGroupConstants.Table.USER_GROUP_FIELD)
public class UserGroupField extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -115811375029158266L;
	public static final String USER_GROUP = "userGroup";
	public static final String DYNAMIC_FIELD_SLOT = "dynamicFieldSlot";

	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usergroup_uuid")
	private UserGroup userGroup;

	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dynamicfieldslot_uuid")
	@OrderBy(DynamicFieldSlot.SORT + " ASC")
	private DynamicFieldSlot dynamicFieldSlot;

	@Audited(withModifiedFlag = true)
	private String value;

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public DynamicFieldSlot getDynamicFieldSlot() {
		return dynamicFieldSlot;
	}

	public void setDynamicFieldSlot(DynamicFieldSlot dynamicFieldSlot) {
		this.dynamicFieldSlot = dynamicFieldSlot;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
