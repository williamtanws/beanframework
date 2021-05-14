package com.beanframework.core.data;

import com.beanframework.common.data.AuditorDto;

public class DataTableResponseData {

  private String uuid;
  private String id;
  private String createdDate;
  private AuditorDto createdBy;
  private String lastModifiedDate;
  private AuditorDto lastModifiedBy;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public AuditorDto getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(AuditorDto createdBy) {
    this.createdBy = createdBy;
  }

  public String getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(String lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public AuditorDto getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(AuditorDto lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }
}
