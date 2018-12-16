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
import com.beanframework.user.UserRightConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserRightConstants.Table.USER_RIGHT)
public class UserRight extends GenericDomain {

	private static final long serialVersionUID = 8192305251381233446L;
	public static final String DOMAIN = "UserRight";
	public static final String USER_RIGHT_FIELDS = "userRightFields";

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserRightField.USER_RIGHT, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserRightField> userRightFields = new ArrayList<UserRightField>();

	private int sort;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public List<UserRightField> getUserRightFields() {
		return userRightFields;
	}

	public void setUserRightFields(List<UserRightField> userRightFields) {
		this.userRightFields = userRightFields;
	}

}
