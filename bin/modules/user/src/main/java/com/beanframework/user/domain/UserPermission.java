package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.UserPermissionConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserPermissionConstants.Table.USER_PERMISSION)
public class UserPermission extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8824467052116434484L;
	public static final String NAME = "name";
	public static final String USER_PERMISSION_FIELD = "userPermissionField";
	public static final String SORT = "sort";
	public static final String ATTRIBUTES = "fields";

	public UserPermission() {
		super();
	}

	public UserPermission(UUID uuid, String id, String name) {
		super();
		setUuid(uuid);
		setId(id);
		setName(name);
	}

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@OrderBy(UserPermissionAttribute.DYNAMIC_FIELD_SLOT)
	private List<UserPermissionAttribute> attributes = new ArrayList<UserPermissionAttribute>();

	@Audited(withModifiedFlag = true)
	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserPermissionAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UserPermissionAttribute> attributes) {
		this.attributes = attributes;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
