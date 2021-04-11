package com.beanframework.core.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.beanframework.common.data.AbstractCsv;

public class WorkflowCsv extends AbstractCsv {

	private String name;
	private String classpath;
	private Boolean active;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new Optional(new Trim()), // id
				new Optional(new Trim()), // name
				new NotNull(new Trim()), // classpath
				new Optional(new Trim(new ParseBool())) // active
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "WorkflowCsv [id=" + id + ",name=" + name + ", classpath=" + classpath + ", workflow=" + active + "]";
	}

}
