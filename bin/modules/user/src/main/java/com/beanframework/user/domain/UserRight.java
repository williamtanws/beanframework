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
import com.beanframework.user.UserRightConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserRightConstants.Table.USER_RIGHT)
public class UserRight extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8192305251381233446L;
	public static final String NAME = "name";
	public static final String ATTRIBUTES = "fields";
	public static final String SORT = "sort";

	public UserRight() {
		super();
	}

	public UserRight(UUID uuid, String id, String name, Integer sort) {
		super();
		setUuid(uuid);
		setId(id);
		setName(name);
		setSort(sort);
	}

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@OrderBy(UserRightAttribute.DYNAMIC_FIELD_SLOT)
	private List<UserRightAttribute> attributes = new ArrayList<UserRightAttribute>();

	@Audited(withModifiedFlag = true)
	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<UserRightAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UserRightAttribute> fields) {
		this.attributes = fields;
	}

}
