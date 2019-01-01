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
import com.beanframework.user.UserRightConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserRightConstants.Table.USER_RIGHT)
public class UserRight extends GenericDomain {

	private static final long serialVersionUID = 8192305251381233446L;
	public static final String FIELDS = "fields";
	public static final String SORT = "sort";

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = UserRightField.USER_RIGHT, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy(UserRightField.DYNAMIC_FIELD)
	private List<UserRightField> fields = new ArrayList<UserRightField>();

	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<UserRightField> getFields() {
		return fields;
	}

	public void setFields(List<UserRightField> fields) {
		this.fields = fields;
	}

}
