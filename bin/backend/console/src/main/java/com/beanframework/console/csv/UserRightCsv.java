package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class UserRightCsv extends AbstractCsv {

	private String name;
	private int sort;
	private String dynamicField;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new UniqueHashCode(), // id
				new NotNull(), // name
				new ParseInt(), // sort
				new Optional() // dynamicField
		};

		return processors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(String dynamicField) {
		this.dynamicField = dynamicField;
	}

}
