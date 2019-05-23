package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class DynamicFieldSlotCsv extends AbstractCsv {

	private String name;
	private Integer sort;
	private String dynamicFieldId;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new NotNull(new Trim()), // id
				new Optional(new Trim()), // name
				new Optional(new Trim(new ParseInt())), // sort
				new Optional(new Trim()),// dynamicFieldId
		};

		return processors;
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

	public String getDynamicFieldId() {
		return dynamicFieldId;
	}

	public void setDynamicFieldId(String dynamicFieldId) {
		this.dynamicFieldId = dynamicFieldId;
	}

	@Override
	public String toString() {
		return "DynamicFieldSlotCsv [id=" + id + ", name=" + name + ", sort=" + sort + ", dynamicFieldId=" + dynamicFieldId + "]";
	}
	
	

}
