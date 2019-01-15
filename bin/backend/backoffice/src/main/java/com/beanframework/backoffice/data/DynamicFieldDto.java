package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTypeEnum;
import com.beanframework.language.domain.Language;

public class DynamicFieldDto extends GenericDto {
	public static final String NAME = "name";
	public static final String FIELD_TYPE = "fieldType";
	public static final String SORT = "sort";
	public static final String REQUIRED = "required";
	public static final String RULE = "rule";
	public static final String FIELD_GROUP = "fieldGroup";
	public static final String LABEL = "label";
	public static final String LANGUAGE = "language";
	
	private String name;

	private DynamicFieldTypeEnum fieldType;

	private Integer sort;

	private Boolean required;

	private String rule;

	private String fieldGroup;

	private String label;

	private Language language;

	private List<DynamicFieldEnumValueDto> values = new ArrayList<DynamicFieldEnumValueDto>();

	private String selected;

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

	public List<DynamicFieldEnumValueDto> getValues() {
		return values;
	}

	public void setValues(List<DynamicFieldEnumValueDto> values) {
		this.values = values;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
