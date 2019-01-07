package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.user.UserPermissionConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserPermissionConstants.Table.USER_PERMISSION)
public class UserPermission extends GenericDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8824467052116434484L;
	public static final String USER_PERMISSION_FIELD = "userPermissionField";
	public static final String SORT = "sort";
	public static final String FIELDS = "fields";

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserPermissionField.USER_PERMISSION, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy(UserPermissionField.DYNAMIC_FIELD)
	private List<UserPermissionField> fields = new ArrayList<UserPermissionField>();

	private Integer sort;

	public List<UserPermissionField> getFields() {
		return fields;
	}

	public void setFields(List<UserPermissionField> fields) {
		this.fields = fields;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
