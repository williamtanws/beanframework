package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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

	private static final long serialVersionUID = 5923500001897510869L;
	public static final String DOMAIN = "UserPermission";
	public static final String USER_PERMISSION_FIELD = "userPermissionField";
	public static final String SORT = "sort";

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserPermissionField.USER_RIGHT, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserPermissionField> userPermissionFields = new ArrayList<UserPermissionField>();

	private int sort;

	public List<UserPermissionField> getUserPermissionFields() {
		return userPermissionFields;
	}

	public void setUserPermissionFields(List<UserPermissionField> userPermissionFields) {
		this.userPermissionFields = userPermissionFields;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
