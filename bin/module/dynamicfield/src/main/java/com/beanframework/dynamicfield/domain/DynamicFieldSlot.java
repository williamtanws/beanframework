package com.beanframework.dynamicfield.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldSlotConstants;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
	@Column(name="dynamicfield_uuid")
	private UUID dynamicField;

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

	public UUID getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(UUID dynamicField) {
		this.dynamicField = dynamicField;
	}
}
