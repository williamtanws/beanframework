package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.dynamicfield.DynamicFieldType;

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

	private String tableSelectedLanguage;

	private String[] tableEnumerations;

	private String[] tableSelectedEnumerations;

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

	public String getTableSelectedLanguage() {
		return tableSelectedLanguage;
	}

	public void setTableSelectedLanguage(String tableSelectedLanguage) {
		this.tableSelectedLanguage = tableSelectedLanguage;
	}

	public String[] getTableEnumerations() {
		return tableEnumerations;
	}

	public void setTableEnumerations(String[] tableEnumerations) {
		this.tableEnumerations = tableEnumerations;
	}

	public String[] getTableSelectedEnumerations() {
		return tableSelectedEnumerations;
	}

	public void setTableSelectedEnumerations(String[] tableSelectedEnumerations) {
		this.tableSelectedEnumerations = tableSelectedEnumerations;
	}

}
