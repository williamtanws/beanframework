package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.beanframework.common.data.AbstractCsv;
import com.beanframework.dynamicfield.DynamicFieldType;

public class DynamicFieldCsv extends AbstractCsv {

	private String name;
	private DynamicFieldType type;
	private Boolean required;
	private String rule;
	private String label;
	private String grid;
	private String language;
	private String enumIds;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new NotNull(new Trim()), // id
				new Optional(new Trim()), // name
				new NotNull(new Trim(new ParseEnum(DynamicFieldType.class, true))), // type
				new Optional(new Trim(new ParseBool())), // required
				new Optional(new Trim()), // rule
				new NotNull(new Trim()), // label
				new NotNull(new Trim()), // grid
				new Optional(new Trim()), // language
				new Optional(new Trim()), // enumIds
		};

		return processors;
	}

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEnumIds() {
		return enumIds;
	}

	public void setEnumIds(String enumIds) {
		this.enumIds = enumIds;
	}

	@Override
	public String toString() {
		return "DynamicFieldCsv [id=" + id + ", name=" + name + ", type=" + type + ", required=" + required + ", rule=" + rule + ", label=" + label + ", grid=" + grid + ", language=" + language + ", enumIds="
				+ enumIds + "]";
	}
	
	

}
