package com.beanframework.catalog.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.catalog.CatalogConstants;
import com.beanframework.common.domain.AbstractDomain;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = CatalogConstants.Table.CATALOG)
public class Catalog extends AbstractDomain {

	private static final long serialVersionUID = 5992760081038782486L;
	public static final String MODEL = "Catalog";
	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String SORT = "sort";

	private String name;
	private Boolean active;
	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
