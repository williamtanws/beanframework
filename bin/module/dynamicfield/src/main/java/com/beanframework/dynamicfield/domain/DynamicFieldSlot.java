package com.beanframework.dynamicfield.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldSlotConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldSlotConstants.Table.DYNAMIC_FIELD_SLOT)
public class DynamicFieldSlot extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9103174021745051522L;
	public static final String DYNAMIC_FIELD_TEMPLATE = "dynamicFieldTemplate";
	public static final String DYNAMIC_FIELD = "dynamicField";
	public static final String SORT = "sort";

	@Audited(withModifiedFlag = true)
	private Integer sort;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dynamicfield_uuid")
	private DynamicField dynamicField;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "template_uuid")
	private DynamicFieldTemplate dynamicFieldTemplate;

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

	public DynamicFieldTemplate getDynamicFieldTemplate() {
		return dynamicFieldTemplate;
	}

	public void setDynamicFieldTemplate(DynamicFieldTemplate dynamicFieldTemplate) {
		this.dynamicFieldTemplate = dynamicFieldTemplate;
	}

}
