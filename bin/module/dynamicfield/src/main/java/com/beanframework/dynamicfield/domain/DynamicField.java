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
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldConstants;
import com.beanframework.language.domain.Language;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldConstants.Table.DYNAMIC_FIELD)
public class DynamicField extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4733112810509713059L;
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String SORT = "sort";
	public static final String REQUIRED = "required";
	public static final String RULE = "rule";
	public static final String LABEL = "label";
	public static final String LANGUAGE = "language";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	@NotNull
	@Enumerated(EnumType.STRING)
	private DynamicFieldType type;

	@Audited(withModifiedFlag = true)
	private Integer sort;

	@Audited(withModifiedFlag = true)
	private Boolean required;

	@Audited(withModifiedFlag = true)
	private String rule;
	
	@Audited(withModifiedFlag = true)
	private String label;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_uuid")
	private Language language;

	@AuditMappedBy(mappedBy = DynamicFieldEnum.DYNAMIC_FIELD) 
	@Cascade({ CascadeType.REFRESH })
	@OneToMany(mappedBy = DynamicFieldEnum.DYNAMIC_FIELD, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy(DynamicFieldEnum.SORT + " ASC")
	private List<DynamicFieldEnum> enums = new ArrayList<DynamicFieldEnum>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DynamicFieldType getType() {
		return type;
	}

	public void setType(DynamicFieldType type) {
		this.type = type;
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

	public List<DynamicFieldEnum> getEnums() {
		return enums;
	}

	public void setEnums(List<DynamicFieldEnum> enums) {
		this.enums = enums;
	}
}
