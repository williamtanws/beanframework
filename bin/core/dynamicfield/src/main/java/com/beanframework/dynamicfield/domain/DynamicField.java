package com.beanframework.dynamicfield.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.dynamicfield.DynamicFieldConstants;
import com.beanframework.language.domain.Language;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldConstants.Table.DYNAMIC_FIELD)
public class DynamicField extends GenericDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4733112810509713059L;
	public static final String FIELD_TYPE = "fieldType";
	public static final String SORT = "sort";
	public static final String REQUIRED = "required";
	public static final String RULE = "rule";
	public static final String FIELD_GROUP = "fieldGroup";
	public static final String LABEL = "label";
	public static final String LANGUAGE = "language";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	@NotNull
	@Enumerated(EnumType.STRING)
	private DynamicFieldTypeEnum fieldType;

	@Audited(withModifiedFlag = true)
	private Integer sort;

	@Audited(withModifiedFlag = true)
	private Boolean required;

	@Audited(withModifiedFlag = true)
	private String rule;

	@Audited(withModifiedFlag = true)
	private String fieldGroup;

	@Audited(withModifiedFlag = true)
	private String label;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_uuid")
	private Language language;

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = DynamicFieldEnumValue.DYNAMIC_FIELD, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy(DynamicFieldEnumValue.SORT + " ASC")
	private List<DynamicFieldEnumValue> values = new ArrayList<DynamicFieldEnumValue>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DynamicFieldTypeEnum getFieldType() {
		return fieldType;
	}

	public void setFieldType(DynamicFieldTypeEnum fieldType) {
		this.fieldType = fieldType;
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

	public String getFieldGroup() {
		return fieldGroup;
	}

	public void setFieldGroup(String fieldGroup) {
		this.fieldGroup = fieldGroup;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public List<DynamicFieldEnumValue> getValues() {
		return values;
	}

	public void setValues(List<DynamicFieldEnumValue> values) {
		this.values = values;
	}

}
