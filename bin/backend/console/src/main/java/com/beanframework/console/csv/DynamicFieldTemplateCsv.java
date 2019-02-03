package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class DynamicFieldTemplateCsv extends AbstractCsv {

	private String name;
	private String dynamicFieldIds;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new UniqueHashCode(), // id
				new NotNull(), //
				new Optional() //
		};
		return processors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDynamicFieldIds() {
		return dynamicFieldIds;
	}

	public void setDynamicFieldIds(String dynamicFieldIds) {
		this.dynamicFieldIds = dynamicFieldIds;
	}

}