package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.user.UserGroupConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserGroupConstants.Table.USER_GROUP)
public class UserGroup extends GenericDomain {

	private static final long serialVersionUID = 8938920413700273352L;
	public static final String DOMAIN = "UserGroup";
	public static final String PARENT = "parent";
	public static final String CHILDS = "childs";
	public static final String USER_AUTHORITIES = "userAuthorities";
	public static final String USER_GROUP_FIELDS = "userGroupFields";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_uuid")
	private UserGroup parent;

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = PARENT, fetch = FetchType.LAZY)
	private List<UserGroup> childs = new ArrayList<UserGroup>();

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserAuthority.USER_GROUP, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<UserAuthority> userAuthorities = new ArrayList<UserAuthority>();

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserGroupField.USER_GROUP, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserGroupField> userGroupFields = new ArrayList<UserGroupField>();

	@Transient
	private String selected;

	public UserGroup getParent() {
		return parent;
	}

	public void setParent(UserGroup parent) {
		this.parent = parent;
	}

	public List<UserGroup> getChilds() {
		return childs;
	}

	public void setChilds(List<UserGroup> childs) {
		this.childs = childs;
	}

	public List<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(List<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public List<UserGroupField> getUserGroupFields() {
		return userGroupFields;
	}

	public void setUserGroupFields(List<UserGroupField> userGroupFields) {
		this.userGroupFields = userGroupFields;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
