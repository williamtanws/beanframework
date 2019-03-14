package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class MediaDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2508601142629579799L;
	public static final String FILENAME = "fileName";
	public static final String FILETYPE = "fileType";
	public static final String FILESIZE = "fileSize";
	public static final String HEIGHT = "height";
	public static final String WIDTH = "width";
	public static final String URL = "url";
	public static final String TITLE = "title";
	public static final String CAPTION = "caption";
	public static final String ALTTEXT = "altText";
	public static final String DESCRIPTION = "description";
	public static final String LOCATION = "location";

	private String fileName;
	private String fileType;
	private Long fileSize;
	private String url;
	private String title;
	private String caption;
	private String altText;
	private String description;
	private String location;

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

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
