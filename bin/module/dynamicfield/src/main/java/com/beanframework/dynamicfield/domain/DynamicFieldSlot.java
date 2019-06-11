package com.beanframework.dynamicfield.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldSlotConstants;

@Cacheable
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldSlotConstants.Table.DYNAMIC_FIELD_SLOT)
public class DynamicFieldSlot extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9103174021745051522L;
	public static final String NAME = "name";
	public static final String SORT = "sort";
	public static final String DYNAMIC_FIELD = "dynamicField";
	public static final String DYNAMIC_FIELD_TEMPLATE = "dynamicFieldTemplates";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	private Integer sort;

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dynamicfield_uuid")
	private DynamicField dynamicField;

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToMany(fetch = FetchType.LAZY)
	private List<DynamicFieldTemplate> dynamicFieldTemplates = new ArrayList<DynamicFieldTemplate>();

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

	public DynamicField getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicField dynamicField) {
		this.dynamicField = dynamicField;
	}

	public List<DynamicFieldTemplate> getDynamicFieldTemplates() {
		return dynamicFieldTemplates;
	}

	public void setDynamicFieldTemplates(List<DynamicFieldTemplate> dynamicFieldTemplates) {
		this.dynamicFieldTemplates = dynamicFieldTemplates;
	}

}
