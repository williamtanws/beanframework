package com.beanframework.backoffice.api.data;

import com.beanframework.core.data.DataTableResponseData;

public class MediaDataTableResponseData extends DataTableResponseData {

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
