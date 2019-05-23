package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class WorkflowCsv extends AbstractCsv {

	private String name;
	private String classpath;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new Optional(new Trim()), // id
				new Optional(new Trim()), // name
				new NotNull(new Trim()) // classpath
		};

		return processors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClasspath() {
		return classpath;
	}

	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

	@Override
	public String toString() {
		return "WorkflowCsv [id=" + id + ",name=" + name + ", classpath=" + classpath + "]";
	}

}
