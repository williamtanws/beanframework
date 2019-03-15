package com.beanframework.backoffice.data;

import com.beanframework.common.data.DataTableResponseData;

public class MediaDataResponse extends DataTableResponseData {

	private String fileName;
	private String fileType;
	private String fileSize;

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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
}
