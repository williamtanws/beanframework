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
import com.beanframework.user.UserConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserConstants.Table.USER_PERMISSION)
public class UserPermission extends GenericDomain {

	private static final long serialVersionUID = 5923500001897510869L;
	public static final String DOMAIN = "UserPermission";
	public static final String USER_PERMISSIONS = "userPermissions";

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserPermissionLang.USER_PERMISSION, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserPermissionLang> userPermissionLangs = new ArrayList<UserPermissionLang>();

	private Integer sort;

	public List<UserPermissionLang> getUserPermissionLangs() {
		return userPermissionLangs;
	}

	public void setUserPermissionLangs(List<UserPermissionLang> userPermissionLangs) {
		this.userPermissionLangs = userPermissionLangs;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
