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
import com.beanframework.dynamicfield.DynamicFieldConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldConstants.Table.DYNAMIC_FIELD_ENUM_VALUE)
public class DynamicFieldEnumValue extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4128353177768556514L;

	public static final String DYNAMIC_FIELD = "dynamicField";
	public static final String VALUE = "value";
	public static final String SORT = "sort";

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dynamicfield_uuid")
	private DynamicField dynamicField;

	@Audited(withModifiedFlag = true)
	private String value;

	@Audited(withModifiedFlag = true)
	private Integer sort;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

}
