package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class MediaCsv extends AbstractCsv {

	private String fileName;
	private String fileType;
	private String url;
	private String title;
	private String caption;
	private String altText;
	private String description;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new NotNull(new Trim()), // id
				new Optional(new Trim()), // fileName
				new Optional(new Trim()), // fileType
				new Optional(new Trim()), // url
				new Optional(new Trim()), // title
				new Optional(new Trim()), // caption
				new Optional(new Trim()), // altText
				new Optional(new Trim()) // description
		};

		return processors;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MediaCsv [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", url=" + url + ", title=" + title + ", caption=" + caption + ", altText=" + altText + ", description=" + description
				+ "]";
	}
	
	

}
