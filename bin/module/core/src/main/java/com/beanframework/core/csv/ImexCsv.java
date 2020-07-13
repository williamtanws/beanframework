package com.beanframework.core.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.beanframework.common.data.AbstractCsv;
import com.beanframework.imex.ImexType;

public class ImexCsv extends AbstractCsv {

	private ImexType type;
	private String directory;
	private String fileName;
	private String query;
	private String header;
	private String seperator;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new NotNull(new Trim()), // id
				new NotNull(new Trim(new ParseEnum(ImexType.class, true))), //
				new Optional(new Trim()), // directory
				new NotNull(new Trim()), // fileName
				new NotNull(new Trim()), // query
				new Optional(new Trim()), // header
				new Optional(new Trim()) // seperator
		};

		return processors;
	}

	public ImexType getType() {
		return type;
	}

	public void setType(ImexType type) {
		this.type = type;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

}
