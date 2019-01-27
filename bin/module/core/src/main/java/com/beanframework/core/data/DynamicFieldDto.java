package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;
import com.beanframework.dynamicfield.domain.DynamicFieldType;

public class DynamicFieldDto extends GenericDto {
	public static final String NAME = "name";
	public static final String FIELD_TYPE = "fieldType";
	public static final String SORT = "sort";
	public static final String REQUIRED = "required";
	public static final String RULE = "rule";
	public static final String LABEL = "label";
	public static final String LANGUAGE = "language";

	private String name;

	private DynamicFieldType type;

	private Integer sort;

	private Boolean required;

	private String rule;

	private String label;

	private LanguageDto language;

	private List<DynamicFieldEnumDto> enums = new ArrayList<DynamicFieldEnumDto>();

	private String languageUuid;

	private String[] tableEnums;

	private String[] tableSelectedEnums;

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

	public LanguageDto getLanguage() {
		return language;
	}

	public void setLanguage(LanguageDto language) {
		this.language = language;
	}

	public List<DynamicFieldEnumDto> getEnums() {
		return enums;
	}

	public void setEnums(List<DynamicFieldEnumDto> values) {
		this.enums = values;
	}

	public String getLanguageUuid() {
		return languageUuid;
	}

	public void setLanguageUuid(String languageUuid) {
		this.languageUuid = languageUuid;
	}

	public String[] getTableEnums() {
		return tableEnums;
	}

	public void setTableEnums(String[] tableEnums) {
		this.tableEnums = tableEnums;
	}

	public String[] getTableSelectedEnums() {
		return tableSelectedEnums;
	}

	public void setTableSelectedEnums(String[] tableSelectedEnums) {
		this.tableSelectedEnums = tableSelectedEnums;
	}

}
