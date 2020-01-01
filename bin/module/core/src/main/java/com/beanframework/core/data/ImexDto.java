package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;
import com.beanframework.imex.ImexType;

public class ImexDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7595669197732666157L;

	private ImexType type;

	private String directory;

	private String fileName;

	private String query;

	private String header;

	private String seperator;

	private List<MediaDto> medias = new ArrayList<MediaDto>();

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

	public List<MediaDto> getMedias() {
		return medias;
	}

	public void setMedias(List<MediaDto> medias) {
		this.medias = medias;
	}

}
