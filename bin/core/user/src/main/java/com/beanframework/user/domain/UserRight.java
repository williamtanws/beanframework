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

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.user.UserConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserConstants.Table.USER_RIGHT)
public class UserRight extends AbstractDomain {

	private static final long serialVersionUID = 8192305251381233446L;
	public static final String MODEL = "UserRight";
	public static final String USER_RIGHT_LANGS = "userRightLangs";

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserRightLang.USER_RIGHT, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserRightLang> userRightLangs = new ArrayList<UserRightLang>();

	private Integer sort;

	public List<UserRightLang> getUserRightLangs() {
		return userRightLangs;
	}

	public void setUserRightLangs(List<UserRightLang> userRightLangs) {
		this.userRightLangs = userRightLangs;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
