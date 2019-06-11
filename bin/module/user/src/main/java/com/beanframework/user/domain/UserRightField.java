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
import com.beanframework.user.UserRightConstants;

@Cacheable
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserRightConstants.Table.USER_RIGHT_FIELD)
public class UserRightField extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7666190244677961254L;
	public static final String LANGUAGE = "language";
	public static final String USER_RIGHT = "userRight";
	public static final String DYNAMIC_FIELD_SLOT = "dynamicFieldSlot";

	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userright_uuid")
	private UserRight userRight;

	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dynamicfieldslot_uuid")
	@OrderBy(DynamicFieldSlot.SORT + " ASC")
	private DynamicFieldSlot dynamicFieldSlot;

	@Audited(withModifiedFlag = true)
	private String value;

	public UserRight getUserRight() {
		return userRight;
	}

	public void setUserRight(UserRight userRight) {
		this.userRight = userRight;
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
