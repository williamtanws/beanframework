package com.beanframework.backoffice.api.data;

import com.beanframework.core.data.DataTableResponseData;

public class ImexDataTableResponseData extends DataTableResponseData {

  private String fileName;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
