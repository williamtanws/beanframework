package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;
import com.beanframework.dynamicfield.DynamicFieldType;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DynamicFieldDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4182237880040624160L;
	public static final String NAME = "name";
	public static final String FIELD_TYPE = "fieldType";
	public static final String SORT = "sort";
	public static final String REQUIRED = "required";
	public static final String RULE = "rule";
	public static final String LABEL = "label";
	public static final String GRID = "grid";
	public static final String LANGUAGE = "language";

	private String name;

	private DynamicFieldType type;

	private Boolean required;

	private String rule;

	private String label;

	private String grid;

	private LanguageDto language;

	private List<EnumerationDto> enumerations = new ArrayList<EnumerationDto>();

	@JsonIgnore
	private String selectedLanguageUuid;
	@JsonIgnore
	private String[] selectedEnumerationUuids;

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

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public LanguageDto getLanguage() {
		return language;
	}

	public void setLanguage(LanguageDto language) {
		this.language = language;
	}

	public List<EnumerationDto> getEnumerations() {
		return enumerations;
	}

	public void setEnumerations(List<EnumerationDto> enumerations) {
		this.enumerations = enumerations;
	}

	public String getSelectedLanguageUuid() {
		return selectedLanguageUuid;
	}

	public void setSelectedLanguageUuid(String selectedLanguageUuid) {
		this.selectedLanguageUuid = selectedLanguageUuid;
	}

	public String[] getSelectedEnumerationUuids() {
		return selectedEnumerationUuids;
	}

	public void setSelectedEnumerationUuids(String[] selectedEnumerationUuids) {
		this.selectedEnumerationUuids = selectedEnumerationUuids;
	}

}
