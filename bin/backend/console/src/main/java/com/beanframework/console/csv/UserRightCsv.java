package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class UserRightCsv extends AbstractCsv {

	private String name;
	private Integer sort;
	private String dynamicFieldSlotIds;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new NotNull(new Trim()), // id
				new Optional(new Trim()), // name
				new Optional(new Trim(new ParseInt())), // sort
				new Optional(new Trim()) // dynamicFieldSlotIds
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

	public String getDynamicFieldSlotIds() {
		return dynamicFieldSlotIds;
	}

	public void setDynamicFieldSlotIds(String dynamicFieldSlotIds) {
		this.dynamicFieldSlotIds = dynamicFieldSlotIds;
	}

	@Override
	public String toString() {
		return "UserRightCsv [id=" + id + ", name=" + name + ", sort=" + sort + ", dynamicFieldSlotIds=" + dynamicFieldSlotIds + "]";
	}
	
	

}
