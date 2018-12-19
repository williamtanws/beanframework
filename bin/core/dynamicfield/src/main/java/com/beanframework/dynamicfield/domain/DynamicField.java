package com.beanframework.dynamicfield.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.dynamicfield.DynamicFieldConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldConstants.Table.DYNAMIC_FIELD)
public class DynamicField extends GenericDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4733112810509713059L;
	public static final String DOMAIN = "DynamicField";
	public static final String TYPE = "type";
	public static final String SORT = "sort";
	public static final String REQUIRED = "required";
	public static final String RULE = "rule";

	private String name;
	@NotNull
	@Enumerated(EnumType.STRING)
	private DynamicFieldTypeEnum type;
	private Integer sort;
	private Boolean required;
	private String rule;

	public DynamicFieldTypeEnum getType() {
		return type;
	}

	public void setType(DynamicFieldTypeEnum type) {
		this.type = type;
	}

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

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

}
