package com.beanframework.console.csv;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class DynamicFieldEnumCsv extends AbstractCsv {

	private String name;
	private int sort;
	private String group;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new UniqueHashCode(), // id
				new NotNull(), // name
				new ParseInt(), // sort
				new NotNull() // group
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}