package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.user.UserConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserConstants.Table.USER_GROUP)
public class UserGroup extends AbstractDomain {

	private static final long serialVersionUID = 8938920413700273352L;
	public static final String MODEL = "UserGroup";
	public static final String USER_GROUP_PERMISSION_RIGHTS = "userAuthorities";

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserAuthority.USER_GROUP, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<UserAuthority> userAuthorities = new ArrayList<UserAuthority>();

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserGroupLang.USER_GROUP, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserGroupLang> userGroupLangs = new ArrayList<UserGroupLang>();

	@Transient
	private String selected;

	public List<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(List<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public List<UserGroupLang> getUserGroupLangs() {
		return userGroupLangs;
	}

	public void setUserGroupLangs(List<UserGroupLang> userGroupLangs) {
		this.userGroupLangs = userGroupLangs;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
